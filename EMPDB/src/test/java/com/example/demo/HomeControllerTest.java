package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@AutoConfigureMockMvc
//@SpringBootTest
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class, // このテストクラスでDIを使えるように指定
    TransactionDbUnitTestExecutionListener.class // @DatabaseSetupや＠ExpectedDatabaseなどを使えるように指定
})
@Transactional
public class HomeControllerTest {
	private static final String JDBC_DRIVER = org.h2.Driver.class.getName();
    private static final String JDBC_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
//	@BeforeEach
//	public void setUp() throws Exception {
//		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/empdb","yuma1208","GLION1208");
//		IDatabaseConnection dbconn = new DatabaseConnection(conn);
//		
//		IDataSet dataset = new FlatXmlDataSetBuilder().build(new File("src/test/resources/testdata/init-data/init-data.xml"));
//		DatabaseOperation.CLEAN_INSERT.execute(dbconn, dataset);
//	}
	
	@BeforeAll
    public static void createSchema() throws Exception {
	        RunScript.execute(JDBC_URL, USER, PASSWORD, "src/test/resources/schema.sql", StandardCharsets.UTF_8, false);
    }

    private IDataSet readDataSet(String dataPath) throws Exception {
        // for XML
        return new FlatXmlDataSetBuilder().build(new File(dataPath));
    }

    private void cleanlyInsert(IDataSet dataSet) throws Exception {
        IDatabaseTester databaseTester = new JdbcDatabaseTester(JDBC_DRIVER, JDBC_URL, USER, PASSWORD);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }
	
	@Test
	@DatabaseSetup("src/test/resources/testdata/init-data/initData.xml")
	void 全件表示のテスト() throws Exception {
		// Arrange
        IDataSet dataSet = readDataSet("src/test/resources/testdata/init-data/initData.xml");
        cleanlyInsert(dataSet);
		
		String sql = "select * from m_employee";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		
		assertEquals(1, list.size());
	}
}
