/**
 * Copyright 2004-2005 jManage.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.appleframework.jmx.core.util;

import java.util.*;
import java.io.*;

/**
 * Date: Nov 7, 2004 6:31:22 PM
 * @author Shashank Bellary
 */
public class UserActivityLogger extends Thread{
    /*  Location of activity log file   */
    private static final String USER_ACTIVITY_LOG_FILE_NAME = "userActivity.log";

    /*  Create the only instance of UserActivityLogger  */
    private static UserActivityLogger logger = new UserActivityLogger();

    /*  Create a private store where all activities can be stored for
        logging.  */
    @SuppressWarnings("rawtypes")
	private Vector activities, activityBuffer;

    /*  This variable is used to control the running of the thread. Setting it
        to true causes the logger to stop.  */
    public static boolean INTERRUPT_THREAD = false;

    /*  This is the default length of "sleep" time for the logger thread
        between writes to the log file  */
    private static final long DEFAULT_SLEEP_TIME = 30000;

    /*  File writer that actually writes to the log file    */
    private PrintWriter logWriter;

    /*  Represents the current logging date */
    private Date date;

    /**
     * The only access to the single instance.
     *
     * @return
     */
    public static UserActivityLogger getInstance(){
        return logger;
    }

    /**
     * Now start the static thread running
     */
    static{
        logger.start();
    }

    /**
     * The constuctor for UserActivityLogger is made private so that only the
     * logger itself can create an instance of itself.
     */
    @SuppressWarnings("rawtypes")
	private UserActivityLogger(){
        super();
        activities = new Vector();
        activityBuffer = new Vector();
        /*  Open the file to write, and set it to append all new entries    */
        try{
            File logDir = new File(CoreUtils.getLogDir());
            logDir.mkdirs();
            File logFile = new File(logDir.getPath() + File.separatorChar +
                    USER_ACTIVITY_LOG_FILE_NAME);
            logWriter = new PrintWriter(new FileWriter(logFile, true));
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    public static String getActivityLogFilePath(){
        return CoreUtils.getLogDir() + File.separatorChar + USER_ACTIVITY_LOG_FILE_NAME;
    }
    /**
     * This ensures that all the activities stored in the local stored are
     * written at regular intervals of time to the designated log file.
     */
    public void run() {
        try{
            while(INTERRUPT_THREAD == false){
                sleep(DEFAULT_SLEEP_TIME);
                writeToFile();
            }
        }catch(java.lang.InterruptedException ie){
            ie.printStackTrace();
        }
    }

    /**
     * This is the function that is accessed by programs or modules that
     * actually want to write a log to the log file.
     *
     * Logs user activity by capturing user actions and writing the same to the
     * userActivityLog file.
     *
     * @param user this is the user who performed the activity.
     * @param activity this is the activity that needs to be written to the
     *        log file.
     */
    @SuppressWarnings("unchecked")
	public synchronized void logActivity(String user, String activity){
        date = Calendar.getInstance(Locale.getDefault()).getTime();
        activity = user.toUpperCase()+" "+activity+" on "+date.toString();
        activities.add(activity);
    }

    /**
     * This writes the accumulated activities to a file.
     */
    private void writeToFile() {
        /*  First clone the activities, so that people can still keep writing
            to the log even while we're writing the log to the disk.    */
        cloneActivities();
        /*  Now write the cloned activities (buffer) to the log file   */
        for(Iterator<?> iterator = activityBuffer.iterator(); iterator.hasNext();){
            String activity = (String)iterator.next();
            logWriter.println(activity);
            logWriter.flush();
        }
    }

    /**
     * This makes a copy of the activity Vector so that it can be written to a
     * file in a non-blocking way.
     */
    @SuppressWarnings("rawtypes")
	private synchronized void cloneActivities() {
        activityBuffer= (Vector) activities.clone();
        activities.clear();
    }

    /**
     * Release resources used when class exits.
     */
    public void finalize() {
        logWriter.close();
        activityBuffer.clear();
        activities.clear();
    }
}