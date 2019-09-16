package json_jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import util.JDBCUtil;

public class JsonDB {
	
	
	public static void main(String[] args) {
		System.out.println(searchEmp("smith"));
	}
	
	
	public static String searchEmp(String ename) {
		String sql = "select e.ename,e.job,nvl(m.ename,' ') as mgr,dname from emp e, emp m, dept d where e.mgr=m.empno(+) and e.deptno=d.deptno and lower(e.ename) like lower('%'||?||'%') order by e.ename";
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		JSONArray array=new JSONArray();
		Map<String, String> map = new HashMap<String, String>();
		try {
			con= JDBCUtil.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1, ename);
			rs=ps.executeQuery();
			while(rs.next()) {
				map.put("ename", rs.getString("ename"));
				map.put("job", rs.getString("job"));
				map.put("mgr", rs.getString("mgr"));
				map.put("dname", rs.getString("dname"));
				JSONObject obj=new JSONObject(map);
				array.add(obj);
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			JDBCUtil.close(con, ps, rs);
		}
		
		
		return JSONArray.toJSONString(array);
	}
	
	public static String searchAllDept() {
		String sql = "select dname,count(ename) as count,round(avg(nvl(sal,0))) as sal from emp e, dept d where e.deptno(+)=d.deptno group by d.deptno,dname";
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		JSONArray array=new JSONArray();
		Map<String, String> map = new HashMap<String, String>();
		try {
			con= JDBCUtil.getConnection();
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()) {
				map.put("dname", rs.getString("dname"));
				map.put("count", rs.getString("count"));
				map.put("sal", rs.getString("sal"));
				JSONObject obj=new JSONObject(map);
				array.add(obj);
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			JDBCUtil.close(con, ps, rs);
		}
		
		
		return JSONArray.toJSONString(array);
	}
	
	
}
