package JDBC;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @ClassName TestConnectionPool
 * @Author Ma5on
 * @Date 2019/3/23 18:16
 */
public class TestConnectionPool {
    public static void main(String[] args) {
        ConnectionPool cp = new ConnectionPool(3);
        for(int i=0;i<100;i++){
            new WorkingThread("working pool"+i,cp).start();
        }
    }

    class WorkingThread extends Thread{
        private  ConnectionPool cp;

        public WorkingThread(String name,ConnectionPool cp){
            super(name);
            this.cp = cp;
        }

        @Override
        public void run(){
            Connection c = cp.getConnection();
            System.out.println(this.getName()+"：\t获取了一根连接,并开始工作");
            try(Statement st = c.createStatement()){
                Thread.sleep(1000);//模拟时耗1s的数据库sql语句
                st.execute("select * from hero")
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cp.returnConnection(c);
        }

    }
}
