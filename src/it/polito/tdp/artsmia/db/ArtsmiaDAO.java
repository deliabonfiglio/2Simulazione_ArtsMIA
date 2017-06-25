package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Exhibition;

public class ArtsmiaDAO {

	public List<ArtObject> listObject() {
		
		String sql = "SELECT * from objects";

		List<ArtObject> result = new ArrayList<>();

		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet res = st.executeQuery();

			while (res.next()) {
				
				result.add(new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title")));
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<Integer> getAnni() {
		String sql= "select distinct e.begin " + 
				"from exhibitions as e " + 
				"order by e.`begin` desc ";
		List<Integer> result = new ArrayList<>();

		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet res = st.executeQuery();

			while (res.next()) {
				
				result.add(res.getInt("begin"));
						
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<Exhibition> getExhibitionsFromYear(int anno) {
		String sql= "select * " + 
				"from exhibitions as e " + 
				"where e.begin>=? ";
		List<Exhibition> result = new ArrayList<>();

		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			
			ResultSet res = st.executeQuery();

			while (res.next()) {
				
				result.add(new Exhibition(res.getInt("exhibition_id"),
						res.getString("exhibition_department"),
						res.getString("exhibition_title"),
						res.getInt("begin"),
						res.getInt("end")));
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public Exhibition getExhibitionWithMoreObjects(int anno){
		String sql= "select count(eo.object_id) as cnt, e.* " + 
				"from exhibitions as e , exhibition_objects as eo " + 
				"where eo.exhibition_id= e.exhibition_id and e.`begin`>=? " + 
				"group by e.exhibition_id " + 
				"order by cnt desc ";
		
		Exhibition ex =null;
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			
			ResultSet res = st.executeQuery();

			if (res.next()) {
				
				ex= new Exhibition(res.getInt("exhibition_id"),
						res.getString("exhibition_department"),
						res.getString("exhibition_title"),
						res.getInt("begin"),
						res.getInt("end"));
			}

			conn.close();
			return ex;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public void getObjectsForExhibition(Exhibition e){
		String sql= "select eo.object_id " + 
				"from exhibition_objects as eo " + 
				"where eo.exhibition_id=? ";
		
		Connection conn = DBConnect.getConnection();

		Set<ArtObject> opere= new HashSet<>();
		
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, e.getExhibition_id());
			
			ResultSet res = st.executeQuery();

			while (res.next()) {
				ArtObject a= new ArtObject(res.getInt("object_id"));
				opere.add(a);
			}

			conn.close();

			e.setOpere(opere);
			
		} catch (SQLException exec) {
			// TODO Auto-generated catch block
			exec.printStackTrace();
		}
	}
}
