package connectionToDatabase;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.*;
import java.time.LocalDate;

public class DataManagement extends Connect{

    private String newGroupName;
    private String fName;
    private String sName;
    private int course;
    private String sGroup;
    private int groupCourse;

    public DataManagement () {

    }

    public DataManagement (String newGroupName, String groupCourse) {
        this.newGroupName = newGroupName;
        this.groupCourse = Integer.parseInt(groupCourse);

    }

    public DataManagement(String fName, String sName,String course, String sGroup){
        this.fName = fName;
        this.sName = sName;
        this.course = Integer.parseInt(course);
        this.sGroup = sGroup;
    }

    private PreparedStatement preparedStatement = null;
    private PreparedStatement preparedStatement2 = null;
    private String sql;
    private String sql2;

    public void InsertDeleteStudentGroup(String action) {
        Connection connection = null;
        try {
            connection = Connect.getConnection();

            // Insert group into database
            if (action == "insertGroup") {
                sql ="INSERT INTO groupTable (group_name, group_course) VALUES (?, ?)";

                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,newGroupName);
                preparedStatement.setInt(2,groupCourse);
                preparedStatement.executeUpdate();
            }

            // Insert student into database
            else if (action == "insertStudent") {
                sql ="INSERT INTO studentTable (student_fname, student_sname, student_group, student_course) VALUES (?, ?, ?, ?)";

                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,fName);
                preparedStatement.setString(2,sName);
                preparedStatement.setString(3,sGroup);
                preparedStatement.setInt(4, course);
                preparedStatement.executeUpdate();
            }

            // Delete group from database
            else if (action == "deleteGroup") {
                sql ="DELETE FROM  groupTable WHERE group_name =? AND group_course =?";

                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,newGroupName);
                preparedStatement.setInt(2,groupCourse);
                preparedStatement.executeUpdate();
            }

            // Delete student from database
            else if (action == "deleteStudent") {
                sql = "DELETE FROM  studentTable WHERE student_fname =? AND student_sname =? AND student_group =? AND student_course =?";

                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,fName);
                preparedStatement.setString(2,sName);
                preparedStatement.setString(3,sGroup);
                preparedStatement.setInt(4,course);
                preparedStatement.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null)
            {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null)
            {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void insertAttendance(LocalDate date , int id, String state) {
        Date d = Date.valueOf(date);
        Connection connection = null;
        try {
            sql ="INSERT INTO attendTable (date, student_id, attend) VALUES (?, ?, ?)";
            connection = Connect.getConnection();

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setDate(1,d);
            preparedStatement.setInt(2,id);
            if (state == "present")
            {
                preparedStatement.setInt(3,1);
            }
            else
            {
                preparedStatement.setInt(3,0);
            }
            preparedStatement.executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (preparedStatement != null)
            {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null)
            {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void insertDate(LocalDate date) {
        Connection connection = null;
        try {
            sql = "SELECT * FROM dateTable WHERE date =?";
            connection = Connect.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, Date.valueOf(date));
            ResultSet results = preparedStatement.executeQuery();
            int counter = 0;

            while (results.next())
            {
                counter++;
            }
            if (counter == 0)
            {
                String sql2 ="INSERT INTO dateTable (date) VALUES (?)";
                connection = Connect.getConnection();

                preparedStatement = connection.prepareStatement(sql2);
                preparedStatement.setDate(1,Date.valueOf(date));
                preparedStatement.executeUpdate();

            }

            } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null)
            {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null)
            {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    public void selectG (String group, String course, LocalDate date, String state) {
        Connection connection = null;
        try {
            sql = "SELECT * FROM studentTable WHERE student_group =? AND student_course =?";
            connection = Connect.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,group);
            preparedStatement.setInt(2,Integer.parseInt(course));

            ResultSet results = preparedStatement.executeQuery() ;

            while (results.next())
            {

                try
                {
                    String sql2 = "DELETE FROM attendTable WHERE date =? AND student_id =?";

                    preparedStatement2  = connection.prepareStatement(sql2);
                    preparedStatement2.setDate(1,Date.valueOf(date));
                    preparedStatement2.setInt(2,results.getInt("idstudent"));
                    preparedStatement2.executeUpdate();

                } catch(Exception e)
                {
                    e.printStackTrace();
                }

                insertAttendance(date,results.getInt("idstudent"),state);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void selectS (String fname, String sname, LocalDate date, String state) {
        Connection connection = null;
        try {
            sql = "SELECT * FROM studentTable WHERE student_fname =? AND student_sname =?";
            connection = Connect.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,fname);
            preparedStatement.setString(2,sname);

            ResultSet results = preparedStatement.executeQuery() ;

            while (results.next())
            {
                try
                {
                    sql2 = "DELETE FROM attendTable WHERE date =? AND student_id =?";

                    preparedStatement2  = connection.prepareStatement(sql2);
                    preparedStatement2.setDate(1,Date.valueOf(date));
                    preparedStatement2.setInt(2,results.getInt("idstudent"));
                    preparedStatement2.executeUpdate();

                } catch(Exception e)
                {
                    e.printStackTrace();
                }

                insertAttendance(date,results.getInt("idstudent"),state);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void createFile(String d1, String d2, String attGroup, String attCourse) {
        // create a file about students attendance in group attGroup
        java.util.Date date1 = java.sql.Date.valueOf(d1), date2 = java.sql.Date.valueOf(d2);
        PrintWriter printWriter = null;
        Connection connection = null;
        try {
            File results = new File("results.txt");
            FileWriter fileWriter = new FileWriter(results,true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            printWriter = new PrintWriter(bufferedWriter);
            PreparedStatement preparedStatement = null;
            printWriter.println(attCourse+ " kurso " + attGroup + " grupės lankomumas nuo " + d1+ " iki " + d2);

            connection = getConnection();
            String sql = "SELECT * FROM dateTable";

            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
            {
                if ((resultSet.getDate("date").after(date1) || resultSet.getDate("date").equals(date1))&& (resultSet.getDate("date").before(date2) ||resultSet.getDate("date").equals(date2)))
                {
                    printWriter.println(String.valueOf(resultSet.getDate("date")));
                    java.sql.Date d_2 = resultSet.getDate("date");

                    try
                    {
                        String sql2 = "SELECT * FROM attendTable WHERE date =?";

                        preparedStatement = connection.prepareStatement(sql2);
                        preparedStatement.setDate(1,d_2 );
                        ResultSet resultSet2 = preparedStatement.executeQuery() ;

                        while (resultSet2.next())
                        {
                            try
                            {
                                String sql3 = "SELECT * FROM studentTable WHERE idstudent =?";

                                preparedStatement = connection.prepareStatement(sql3);
                                preparedStatement.setInt(1,resultSet2.getInt("student_id"));
                                ResultSet resultSet3 = preparedStatement.executeQuery() ;


                                while(resultSet3.next())
                                {
                                    if (resultSet2.getInt("attend") == 1)
                                    {

                                        printWriter.println(String.valueOf(resultSet3.getString("student_fname")) + " " +resultSet3.getString("student_sname") + " dalyvavo" );

                                    }
                                    else
                                    {

                                        printWriter.println(String.valueOf(resultSet3.getString("student_fname"))+ " " +resultSet3.getString("student_sname") + " nedalyvavo");
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (printWriter != null){
            printWriter.close();
        }
    }
}
