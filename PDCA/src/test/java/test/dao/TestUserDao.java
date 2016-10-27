package test.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tedu.cloudnote.dao.UserDao;
import com.tedu.cloudnote.entity.User;
import com.tedu.cloudnote.util.NoteUtil;

public class TestUserDao {
	public UserDao dao;
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
				"userDao",UserDao.class);
	}
	/*
	@Test
	public void test1() throws SQLException{
		//创建Spring容器

		User user = dao.findByName("");
		if(user==null){
			System.out.println("用户不存在");
		}else{
			System.out.println(user.getU_password());
		}
	}
	*/
	/*
	@Test
	public void test2() throws Exception{
		User u=new User();
		u.setU_name("以1后");
		String md5_pwd = NoteUtil.md5("mm123456");
		u.setU_password(md5_pwd);
		System.out.println(NoteUtil.createId().length());
		System.out.println("len:"+md5_pwd.length());
		u.setU_phone("15658592588");
		dao.save(u);
		System.out.println("123");
	}
	*/
	//测试模糊查询功能
	@Test
	public void test3(){
		List<User>list=dao.searchByName("%dem%");
		System.out.println("len:"+list.size());
	}

}
