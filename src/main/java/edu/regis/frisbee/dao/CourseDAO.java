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
package edu.regis.frisbee.dao;

import edu.regis.frisbee.err.NonRecoverableException;
import edu.regis.frisbee.err.ObjNotFoundException;
import edu.regis.frisbee.model.Course;
import edu.regis.frisbee.svc.CourseSvc;

/**
 *
 * @author rickb
 */
public class CourseDAO implements CourseSvc {

    @Override
    public Course retrieve(int id) throws ObjNotFoundException, NonRecoverableException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
