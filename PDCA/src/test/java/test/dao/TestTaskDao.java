package test.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tedu.cloudnote.dao.TaskDao;
import com.tedu.cloudnote.entity.Task;
import com.tedu.cloudnote.entity.User;
import com.tedu.cloudnote.util.NoteUtil;

public class TestTaskDao {
	public TaskDao dao;
	@Before
	public void before() throws SQLException{
		String[] conf = {"conf/spring-mvc.xml",
		"conf/spring-mybatis.xml"};
		ApplicationContext ac = 
				new ClassPathXmlApplicationContext(conf);
		//获取DataSource
		DataSource ds = ac.getBean(
				"dbcp",DataSource.class);
		Connection con = ds.getConnection();
		System.out.println(con);
		con.close();
		//获取SqlSessionFactory
		SqlSessionFactory factory = 
				ac.getBean("ssf",SqlSessionFactory.class);
		System.out.println(factory.openSession());
		//获取UserDao
		dao = ac.getBean(
				"taskDao",TaskDao.class);
	}
	//查询任务 通过pid的List
	@Test
	public void test1(){
		List<String>list=new ArrayList<String>();
		list.add("e8d61157f911480db3d8fd1ad2706c34");
		
	}

}
