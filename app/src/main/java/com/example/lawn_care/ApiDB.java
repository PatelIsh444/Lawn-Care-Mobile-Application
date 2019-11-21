package com.example.lawn_care;

public class ApiDB {
//    private static final String ROOT_URL = "http://35.16.126.96/Android/";
//    private static final String ROOT_URL="http://10.0.2.2:80/scripts/";

    private static final String ROOT_URL = "http://lawn-care.us-east-1.elasticbeanstalk.com/";

//    http://lawn-care.us-east-1.elasticbeanstalk.com/
    
    public static final String URL_DELETE_WORKER_PROFILE = ROOT_URL + "deleteWorkerProfile.php";

    public static final String URL_GET_Your_Worker_Profile = ROOT_URL + "viewYourWorkerProfile.php";

    public static final String URL_UPDATE_WORKER_PROFILE = ROOT_URL + "updateWorkerProfile.php";

    public static final String URL_GET_WORKER_LIST = ROOT_URL + "testGetWorkerList.php";

    public static final String URL_SUBMIT = ROOT_URL + "submit.php";

    public static final String URL_TESTING = ROOT_URL + "test.php";

    public static final String URL_SEARCH=ROOT_URL+"getPropertyWork.php";

    public static final String URL_PROPERTY_INFO=ROOT_URL+"getPropertyInfo.php";

    public static final String URL_SET_USER_RATING = ROOT_URL + "setUserRating.php";

    public static final String URL_GET_USER_RATING = ROOT_URL + "getUserRating.php";
}
