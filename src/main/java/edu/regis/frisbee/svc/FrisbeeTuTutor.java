/*
 * Frisbee: Formal Logic Tutor
 * 
 *  (C) Richard Blumenthal, All rights reserved
 * 
 *  Unauthorized use, duplication or distribution without the authors'
 *  permission is strictly prohibited.
 * 
 *  Unless required by applicable law or agreed to in writing, this
 *  software is distributed on an "AS IS" basis without warranties
 *  or conditions of any kind, either expressed or implied.
 */
package edu.regis.frisbee.svc;

import edu.regis.frisbee.err.IllegalArgException;
import edu.regis.frisbee.err.NonRecoverableException;
import edu.regis.frisbee.err.ObjNotFoundException;
import edu.regis.frisbee.model.Account;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.regis.frisbee.model.Course;
import edu.regis.frisbee.model.Step;
import edu.regis.frisbee.model.StepCompletion;
import edu.regis.frisbee.model.Student;
import edu.regis.frisbee.model.Task;
import edu.regis.frisbee.model.TutoringSession;
import edu.regis.frisbee.model.Unit;
import edu.regis.frisbee.model.User;
import edu.regis.frisbee.model.aol.Assessment;
import edu.regis.frisbee.model.aol.AssessmentLevel;
import edu.regis.frisbee.model.aol.KnowledgeComponent;
import edu.regis.frisbee.model.aol.StudentModel;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The ShaTu tutor, which implements the tutoring service.
 *
 * @author rickb
 */
public class FrisbeeTuTutor implements TutorSvc {

    /**
     * The id of the default course taught by the this tutor.
     */
    private static final int DEFAULT_COURSE_ID = 1;
    /**
     * The maximum number of characters allowed for encoding a example ASCII
     * encoding request from the student.
     */
    private static final int MAX_ASCII_SIZE = 20;

    private static final int MAX_BITS_SIZE = 32;

    /**
     * Handler for logging non-exception messages from this class versus thrown
     * exception, which are logged by the exception.
     */
    private static final Logger LOGGER
            = Logger.getLogger(FrisbeeTuTutor.class.getName());

    /**
     * The current tutoring session, which contains information on the current
     * Student, StudentModel, Course, Task, Step, etc.
     */
    private TutoringSession session;

    /**
     * Convenience reference to the current gson object.
     */
    private Gson gson;

    /**
     * Initialize the tutor singleton (a NoOp).
     */
    public FrisbeeTuTutor() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TutorReply request(ClientRequest request) {
        // Uses reflection to invoke a method derived from the request name in
        // the client request (e.g., ":SignIn" invokes "signIn(...)").
        Logger.getLogger(FrisbeeTuTutor.class.getName()).log(Level.INFO, request.getRequestType().getRequestName());

        // Efficiently produce "signIn" from ":SignIn", for example.         
        char c[] = request.getRequestType().getRequestName().toCharArray();
        c[1] = Character.toLowerCase(c[1]);

        char m[] = new char[c.length - 1];
        for (int i = 1; i < c.length; i++) {
            m[i - 1] = c[i];
        }

        String methodName = new String(m);

        // Most methods require verifying the given security token with the known one.
        switch (methodName) {
            case "completedStep":
            case "completedTask":
            case "newExample":
            case "requestHint":
                try {
                session = verifySession(request.getUserId(), request.getSessionId());

            } catch (ObjNotFoundException ex) {
                return createError("No session exists for user: " + request.getUserId(), ex);
            } catch (IllegalArgException ex) {
                return createError("Illegal session token sent for user: " + request.getUserId(), ex);
            } catch (NonRecoverableException ex) {
                return createError(ex.toString(), ex);
            }

            String msg = "Session verified for " + request.getUserId();
            Logger.getLogger(FrisbeeTuTutor.class.getName()).log(Level.INFO, msg);
            break;

            default: // e.g., signIn itself, newAccount
                Logger.getLogger(FrisbeeTuTutor.class.getName()).log(Level.INFO, "No token verification required");
        }

        // Security token has been verified or not required (e.g., signIn, createAccount).
        try {
            Method method = getClass().getMethod(methodName, String.class);

            return (TutorReply) method.invoke(this, request.getData());

        } catch (NoSuchMethodException ex) {
            return createError("Tutor received an unknown request type: " + request.getRequestType().getRequestName(), ex);
        } catch (SecurityException ex) {
            return createError("ShaTuTutor_ERR_2", ex);
        } catch (IllegalAccessException ex) {
            return createError("ShaTuTutor_ERR_3", ex);
        } catch (IllegalArgumentException ex) {
            return createError("ShaTuTutor_ERR_4", ex);
        } catch (InvocationTargetException ex) {
            return createError("ShaTuTutor_ERR_5", ex);
        }
    }

    /**
     * Creates a new student account
     *
     * This method handles ":CreateAccount" requests from the GUI client.
     *
     * @param jsonAcct a JSon encoded Account object
     * @return a TutorReply if successful the status is "Created", otherwise the
     * status is "ERR".
     */
    public TutorReply createAccount(String jsonAcct) throws NonRecoverableException {
       // gson = new GsonBuilder().setPrettyPrinting().create();

        Account acct = gson.fromJson(jsonAcct, Account.class);

        int courseId = DEFAULT_COURSE_ID; // Currently only one course

        StudentSvc stuSvc = ServiceFactory.findStudentSvc();

        if (stuSvc.exists(acct.getUserId()))
            return new TutorReply("IllegalUserId");

        try {
            ServiceFactory.findUserSvc().create(acct);

            try {
                CourseSvc courseSvc = ServiceFactory.findCourseSvc();

                Course course = courseSvc.retrieve(courseId);

                session = createSession(acct, course);

                createStudent(acct, course, session);

                return new TutorReply("Created");

            } catch (ObjNotFoundException ex) {
                return createError("Unknown course: " + courseId, null);
            }

        } catch (IllegalArgException ex) {
            // Should never get here since we tested whether the account exists
            return new TutorReply("IllegalUserId");
        }
    }

    /**
     * Attempts to sign a student in.
     *
     * This method handles ":SignIn" requests from the GUI client.
     *
     * @param jsonUser a JSon encoded User object
     * @return a TutorReply, if successful, the status is "Authenticated" with
     * data being a JSon encoded TutoringSession object.
     */
    public TutorReply signIn(String jsonUser) {
        System.out.println("Received sign in: " + jsonUser);
        //gson = new GsonBuilder().setPrettyPrinting().create();

        User user = gson.fromJson(jsonUser, User.class
        );

        try {
            User dbUser = ServiceFactory.findUserSvc().retrieve(user.getUserId());

            if (dbUser.getPassword().equals(user.getPassword())) {
                SessionSvc svc = ServiceFactory.findSessionSvc();
                TutoringSession session = svc.retrieve(user.getUserId());

                TutorReply reply = new TutorReply("Authenticated");

                reply.setData(gson.toJson(session));

                return reply;

            } else {
                return new TutorReply("InvalidPassword");

            }

        } catch (ObjNotFoundException e) {
            return new TutorReply("UnknownUser");

        } catch (NonRecoverableException ex) {
            Logger.getLogger(FrisbeeTuTutor.class
                    .getName()).log(Level.SEVERE, null, ex);
            return new TutorReply();

        }
    }

    /**
     * Returns a hint to the GUI client, if any
     *
     * This method handles ":RequestHint" requests from the GUI client.
     *
     * @param sessionInfo a
     * @return a TutorReply, if successful, the status is "Hint" with data being
     * a displayable hint text string.
     */
    public TutorReply requestHint(String sessionInfo) {
        // ToDo: this is simply a hard coded test case
        TutorReply reply = new TutorReply("Hint");
        reply.setData("This is a hint from the tutor.");

        return new TutorReply();
    }

    /**
     * 
     * 
     * @param jsonObj a JSon encoded StepCompletion object
     * @return 
     */
    public TutorReply completedStep(String jsonObj) {
        System.out.println("completedStep");
        StepCompletion completion = gson.fromJson(jsonObj, StepCompletion.class);
        
        Step step = completion.getStep();
        
        switch (step.getSubType()) {
            case INFO_MESSAGE:
                return completeInfoMsgStep(completion);
                
            default:
                return createError("Unknown step completion: " + step.getSubType(), null);
        }
    }
    
    public TutorReply completeRotateStep(StepCompletion completion) {
        TutorReply reply = new TutorReply(":StepCompletionReply");
        
        return reply;
    }
    
    public TutorReply completeInfoMsgStep(StepCompletion completion) {
        TutorReply reply = new TutorReply(":StepCompletionReply");
        
        return reply;
    }
    
    
    
   /*
                
    public TutorReply completeChoiceStep(StepCompletion completion) {
        System.out.println("Tutor completeChoiceStep");
        
        ChoiceFunctionStep example = gson.fromJson(completion.getData(), ChoiceFunctionStep.class);
        String operand1 = example.getOperand1();
         String operand2 = example.getOperand2();
        
          String operand3 = example.getOperand3();
        int bitLength = example.getBitLength();
        String result = example.getResult();
       // System.out.println("Result: " + example.getResult());
        
        String expectedResult = choiceFunction(operand1, operand2, operand3, bitLength);
        //System.out.println("Expected result: " + expectedResult);
  
        StepCompletionReply stepReply = new StepCompletionReply();
        
        if (expectedResult.equals(result)) {
            stepReply.setIsCorrect(true);
            stepReply.setIsRepeatStep(false);
            stepReply.setIsNewStep(true);
             
            // ToDo: Use the student model to figure out whether we want
            // to give the student another practice problem of the same
            // type or move on to an entirely different problem.
            stepReply.setIsNewTask(true);
            
            // ToDo: currently only one step in a task, so there isn't a next one???
            stepReply.setIsNextStep(false);
            
        } else {
            stepReply.setIsCorrect(false);
            stepReply.setIsRepeatStep(true);
            stepReply.setIsNewStep(false);
            stepReply.setIsNewTask(false);
            stepReply.setIsNextStep(false);
        }
     
        Step step = new Step(1, 0, StepSubType.STEP_COMPLETION_REPLY);
        step.setCurrentHintIndex(0);
        step.setNotifyTutor(true);
        step.setIsCompleted(false);
        // ToDo: fix timeouts
        Timeout timeout = new Timeout("Complete Step", 0, ":No-Op", "Exceed time");
        step.setTimeout(timeout);
        step.setData(gson.toJson(stepReply));
        
        Task task = new Task();
        task.setKind(TaskKind.PROBLEM);
        task.setType(ExampleType.STEP_COMPLETION_REPLY);
        task.setDescription("Choose your next action");
        task.addStep(step); 
        
        TutorReply reply = new TutorReply(":Success");
    
        reply.setData(gson.toJson(task));
        
        return reply;                
    }
    */

    public TutorReply completedTask(String taskInfo) {
        return new TutorReply();
    }

    /**
     * Handles :NewExample requests from the client.
     *
     * @param json a JSon String encoding a NewExampleRequest object
     * @return TutorReply
     */
    public TutorReply newExample(String json) {
        return new TutorReply();
        //gson = new GsonBuilder().setPrettyPrinting().create();

       // NewExampleRequest request = gson.fromJson(json, NewExampleRequest.class);

       // switch (request.getExampleType()) {
        //    case CHOICE_FUNCTION:
        //        return newChoiceFunctionExample(session, request.getData());

         //   default:
         //       return createError("Unknown new example request: " + request.getExampleType(), null);
       // }
    }

    /**
     * Create and save a new tutoring session associated with the given account.
     *
     * @param account the student user
     * @throws NonRecoverableException
     * @return the new TutoringSession
     */
    private TutoringSession createSession(Account account, Course course) throws NonRecoverableException {
        try {
            Task task = getFirstTask(course);

            TutoringSession tSession = new TutoringSession();
            tSession.setAccount(account);

            Random rnd = new Random();
            String clearToken = "Session" + account.getUserId() + Integer.toString(rnd.nextInt());
            tSession.setSecurityToken(SHA_256.instance().sha256(clearToken));

            tSession.setCourse(course.getDigest());

            Unit unit = course.currentUnit();
            if (unit != null)
                tSession.setUnit(unit.getDigest());
         
            tSession.addTask(task);

            ServiceFactory.findSessionSvc().create(tSession);

            return tSession;

        } catch (IllegalArgException ex) {
            // Should never get here
            throw new NonRecoverableException("Session already exists " + account.getUserId());
        }
    }

    /**
     * Create and save the student and their initial student model.
     *
     * @param acct
     * @param course
     * @return
     */
    private Student createStudent(Account acct, Course course, TutoringSession session)
            throws NonRecoverableException {
        
        Student student = new Student(acct.getUserId(), acct.getPassword());
        StudentModel model = student.getStudentModel();

        try {
            // As the student has at least one task and step to complete,
            // add the associated knowledge component assessment(s) to the 
            // student model of the student.
            HashSet<Integer> componentIds = new HashSet<>();
            for (Task task : session.getTasks()) {
                for (int componentId : task.getExercisedComponentIds()) {
                    componentIds.add(componentId);
                }

                for (Step step : task.getSteps()) {
                    for (int cid : step.getExercisedComponentIds()) {
                        componentIds.add(cid);
                    }
                }
            }

            for (int id : componentIds) {
                if (!model.containsAssessment(id)) {
                    KnowledgeComponent comp = course.findKnowledgeComponent(id);
                    model.addAssessment(id, new Assessment(comp, AssessmentLevel.VERY_LOW));
                    
                    
                }
            }

            StudentSvc svc = ServiceFactory.findStudentSvc();
            svc.create(student);

            return student;

        } catch (IllegalArgException e) {
            // We should never get here since 
            throw new NonRecoverableException("Student already exists " + acct.getUserId());

        } catch (ObjNotFoundException e) {
            throw new NonRecoverableException("Inconsistent Course in DB knowledge component" + course.getId());
        }

    }

    /**
     * Verify that the user with the given id has a session with the given
     * session id.
     *
     * @param userId String "user@regis.edu"
     * @param sessionId String identifying a previously generated session id.
     * @return the current TutoringSession associated with the given user id and
     * session id
     */
    private TutoringSession verifySession(String userId, String sessionId)
            throws ObjNotFoundException, IllegalArgException, NonRecoverableException {

        SessionSvc svc = ServiceFactory.findSessionSvc();
        TutoringSession locSession = svc.retrieve(userId);

        if (locSession.getSecurityToken().equals(sessionId)) {
            return locSession;
        } else {
            throw new IllegalArgException("Illegal session id for user: " + userId);
        }
    }

    /**
     * Return the first task that should be performed in the given course.
     *
     * @param course
     * @return a Task that should be completed first.
     * @throws IllegalArgException see the message text.
     */
    private Task getFirstTask(Course course) throws IllegalArgException {
        switch (course.getPrimaryPedagogy()) {
            case STUDENT_CHOICE:
                return null; // ToDo

            case FIXED_SEQUENCE:
                Unit unit = course.findUnitBySequenceId(0);

                if (unit == null) {
                    throw new IllegalArgException("Unit 0 not found in course: " + course.getId());
                }

                Task task = unit.findTaskBySequence(0);

                if (task == null) {
                    throw new IllegalArgException("Task 0 not found in Unit 0 of course: " + course.getId());
                }

                return task;

            case MASTERY_LEARNING:
                return null; // ToDo

            case MICROADAPTATION:
                return null; // ToDo

            default:
                throw new IllegalArgException("Unknwon task selection in course: " + course.getId());
        }
    }

   


    /**
     * Handles client requests for a new choice function example.
     *
     * @return a TutorReply
     */
    private TutorReply newChoiceFunctionExample(TutoringSession session, String jsonData) {
        /*
        System.out.println("newChoiceFunctionExample");
        ChoiceFunctionStep substep = gson.fromJson(jsonData, ChoiceFunctionStep.class);
        
        int bitLength = substep.getBitLength();
        
        String operand1 = generateInputString(bitLength);
        String operand2 = generateInputString(bitLength);
        String operand3 = generateInputString(bitLength);
        
        substep.setOperand1(operand1);
        substep.setOperand2(operand2);
        substep.setOperand3(operand3);
        
        substep.setResult(choiceFunction(operand1, operand2, operand3, bitLength));
        
        Step step = new Step(1, 0, StepSubType.CHOICE_FUNCTION);
        step.setCurrentHintIndex(0);
        step.setNotifyTutor(true);
        step.setIsCompleted(false);
        // ToDo: fix timeouts
        Timeout timeout = new Timeout("Complete Step", 0, ":No-Op", "Exceed time");
        step.setTimeout(timeout);

        step.setData(gson.toJson(substep));

        Task task = new Task();
        task.setKind(TaskKind.PROBLEM);
        task.setType(ExampleType.CHOICE_FUNCTION);
        task.setDescription("Compute the result of the choice function on the three operands");
        task.addStep(step);  
      
        
        TutorReply reply = new TutorReply(":Success");
        reply.setData(gson.toJson(task));
        
System.out.println("before reply return");
        return reply;
        */
        return new TutorReply();
    }
    
     /**
     * Evaluates the choice function Ch(x, y, z).
     *
     * @param x Binary string representation of x.
     * @param y Binary string representation of y.
     * @param z Binary string representation of z.
     * @return Binary string result of Ch(x, y, z).
     */
    private String choiceFunction(String x, String y, String z, int bitLength) {
        /*
        // Convert the binary strings to integer values
        String tempX = x.replaceAll("\\s", "");
        String tempY = y.replaceAll("\\s", "");
        String tempZ = z.replaceAll("\\s", "");

        long intX = Long.parseLong(tempX, 2);
        long intY = Long.parseLong(tempY, 2);
        long intZ = Long.parseLong(tempZ, 2);

        long xy = intX & intY;

        long notX = ~intX & intZ;

        long result = xy ^ notX;

        // Convert the result back to binary string
        String binaryResult = formatResult(result, bitLength);

        return binaryResult;
        */
        return "";
    }
    
  

    /**
     * Utility for logging an error and an creating a tutoring reply error with
     * the given message, and optional originating exception.
     *
     * @param errMsg a displayable error message
     * @param ex the original exception, if any, that caused the error,
     * otherwise null.
     * @return a TutorReply with an ":ERR" status
     */
    private TutorReply createError(String errMsg, Exception ex) {
        if (ex == null) {
            Logger.getLogger(FrisbeeTuTutor.class.getName()).log(Level.SEVERE, errMsg);
        } else {
            Logger.getLogger(FrisbeeTuTutor.class.getName()).log(Level.SEVERE, errMsg, ex);
        }

        return new TutorReply(":ERR", errMsg);
    }
}

