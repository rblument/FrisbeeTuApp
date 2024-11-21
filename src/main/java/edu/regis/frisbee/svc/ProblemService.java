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

import java.util.List;

/**
 *
 * @author diegoberumen
 */
public class ProblemService {
    
    public Problem getProblemById(int id) {
        
        if (id == 1) { 
            String[] headers = {"A", "B", "C", "A->B", "B->C", "(A->B) ^ (B->C)"};
            String[][] rows = {
                {"T", "T", "T", "T", "T", "T"},
                {"T", "T", "F", "T", "F", "F"},
                {"T", "F", "T", "F", "T", "F"},
                {"T", "F", "F", "F", "F", "F"},
                {"F", "T", "T", "T", "T", "T"},
                {"F", "T", "F", "T", "F", "F"},
                {"F", "F", "T", "T", "T", "T"},
                {"F", "F", "F", "T", "F", "F"}
            };
            return new Problem(headers, rows, rows);
        }
        
        return null;
        
    }
    
    
    public static class Problem {
        private final String[] headers;
        private final String[][] rows;
        private final String[][] correctAnswers;

        public Problem(String[] headers, String[][] rows, String[][] correctAnswers) {
            this.headers = headers;
            this.rows = rows;
            this.correctAnswers = correctAnswers;
        }

        public String[] getHeaders() {
            return headers;
        }

        public String[][] getRows() {
            return rows;
        }
        
        public String[][] getCorrectAnswers() { 
            return correctAnswers;
        }
    }
    
}
