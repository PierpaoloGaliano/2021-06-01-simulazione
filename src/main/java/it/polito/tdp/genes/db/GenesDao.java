package it.polito.tdp.genes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.genes.model.Genes;
import it.polito.tdp.genes.model.Interaction;

public class GenesDao {

	public List<Genes> getAllGenes() {
		String sql = "SELECT DISTINCT GeneID, Essential, Chromosome FROM Genes";
		List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), res.getString("Essential"), res.getInt("Chromosome"));
				result.add(genes);
			}
			res.close();
			st.close();
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Interaction> getInteraction() {
		String sql = "SELECT i.GeneID1,i.GeneID2, ABS(i.Expression_Corr) AS peso "
				+ "FROM interactions i "
				+ "WHERE i.GeneID1<>i.GeneID2 "
				+ "GROUP BY GeneID1,GeneID2";
		List<Interaction> result = new ArrayList<Interaction>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Interaction interaction = new Interaction(res.getString("GeneID1"), res.getString("GeneID2"), res.getDouble("peso"));
				result.add(interaction);
			}
			res.close();
			st.close();
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
