import java.sql.*;

public class Main {

    public  static void main(String[] args) throws SQLException {
        //load the driver
        DriverManager.registerDriver( new oracle.jdbc.OracleDriver());
//Create a connection
        Connection conn =
                DriverManager.getConnection("jdbc:oracle:thin:@10.170.138.40:1521:orclE",
                        "sbd51162", "51162");
        try {
            // Do what you have to do
            //EX1
           /* PreparedStatement query = conn.prepareStatement("SELECT alunos.nome, alunos.sexo, alunos.cod_curso, cursos.nome as ncurso FROM alunos INNER JOIN" +
                    " cursos ON alunos.cod_curso = cursos.cod_curso");
            ResultSet rs = query.executeQuery(); //ResultSet is a Cursor
            while(rs.next()) { //In the beginning, points to before the first row
                String nome = rs.getString("nome"); //get by column label
                String sexo = rs.getString("sexo");
                String nomecurso = rs.getString("ncurso");
                if(sexo.equals("M")){
                    System.out.println(nome + " esta inscrito em "+ nomecurso );
                }
                else{
                    System.out.println(nome + " esta inscrita em " + nomecurso  );

                }

            }
            rs.close();
            query.close();
            //conn.close();*/

            //EX2
            /*PreparedStatement query = conn.prepareStatement("SELECT num_aluno\n" +
                    "FROM (\n" +
                    "  SELECT alunos.num_aluno\n" +
                    "  FROM ALUNOS \n" +
                    "  INNER JOIN inscricoes \n" +
                    "  ON alunos.num_aluno = inscricoes.num_aluno \n" +
                    "  GROUP BY alunos.num_aluno \n" +
                    "  ORDER BY COUNT(*) DESC\n" +
                    ")\n" +
                    "WHERE ROWNUM <= ?");
            query.setInt(1, 10);
            ResultSet rs = query.executeQuery(); //ResultSet is a Cursor
            while(rs.next()) { //In the beginning, points to before the first row
                String num_aluno = rs.getString("num_aluno"); //get by column label
                System.out.println(num_aluno);
            }
            rs.close();
            query.close();
            //conn.close();*/

            //EX3
            /*String nome = "Fred";
            String subject = "Inteligencia Artificial";

            PreparedStatement selectStudents =
                    conn.prepareStatement("SELECT nome, num_aluno, cod_curso FROM ALUNOS WHERE nome = ?");
            selectStudents.setString(1, nome);
            ResultSet rStudent = selectStudents.executeQuery();


            PreparedStatement selectSubject = conn.prepareStatement("SELECT cod_cadeira, nome FROM CADEIRAS WHERE nome = ?");
            selectSubject.setString(1,subject);
            ResultSet rSubject = selectSubject.executeQuery();


            while (rStudent.next()){
                String num_aluno = rStudent.getString("num_aluno");
                String cod_curso = rStudent.getString("cod_curso");
                System.out.println(rStudent.getString("nome"));
                while (rSubject.next()){
                    String cod_cadeira = rSubject.getString("cod_cadeira");
                    System.out.println(rSubject.getString("nome"));
                    PreparedStatement insert =
                            conn.prepareStatement("INSERT INTO inscricoes Values(?, ?, ?, CURRENT_DATE , null, null)");
                    insert.setString(1,num_aluno);
                    insert.setString(2, cod_curso);
                    insert.setString(3, cod_cadeira);
                    insert.executeUpdate();
                }
            }*/


            //EX4/5
            PreparedStatement selectStudents =
                    conn.prepareStatement("SELECT nome, num_aluno, cod_curso FROM ALUNOS");
            ResultSet rStudent = selectStudents.executeQuery();



            while (rStudent.next()){
                String cod_curso = rStudent.getString("cod_curso");
                String nome = rStudent.getString("nome");
                String num_aluno = rStudent.getString("num_aluno");
                PreparedStatement selectCurso = conn.prepareStatement("SELECT cod_curso, nome FROM CURSOS WHERE cod_curso = "+cod_curso);
                ResultSet rCurso = selectCurso.executeQuery();


                while (rCurso.next()){
                    System.out.println(nome+ " "+ rCurso.getString("nome"));
                    System.out.print("Cadeiras inscritas: ");
                    PreparedStatement selectSubject = conn.prepareStatement("SELECT num_aluno, cod_cadeira FROM INSCRICOES WHERE num_aluno = "+num_aluno);
                    ResultSet rSubject = selectSubject.executeQuery();
                    while (rSubject.next()){
                        String cod_cadeira = rSubject.getString("cod_cadeira");
                        PreparedStatement select = conn.prepareStatement("SELECT nome FROM cadeiras WHERE cod_cadeira = "+ cod_cadeira);
                        ResultSet r = select.executeQuery();
                        while (r.next()){
                            System.out.print(r.getString("nome")+", ");

                        }

                    }
                    System.out.println("");

                }

            }


        } finally {
            conn.close();
        }
    }
}
