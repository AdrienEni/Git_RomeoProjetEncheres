package eni.fr.javaee.projet.dal.jdbc;

import fr.eni.javaee.projet.dal.ArticleDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import eni.fr.javaee.projet.bll.BLLException;
import eni.fr.javaee.projet.bll.EnchereManager;
import eni.fr.javaee.projet.bll.UtilisateurManager;
import eni.fr.javaee.projet.bo.ArticleVendu;
import eni.fr.javaee.projet.bo.Enchere;
import fr.eni.javaee.projet.dal.DALException;


public class ArticleDAOJdbcImpl implements ArticleDAO {

	private static final String AFFICHER_VENTES_EN_COURS = "SELECT no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, a.no_utilisateur, no_categorie\r\n " + 
			" from ARTICLES_VENDUS a inner join UTILISATEURS u on a.no_utilisateur = u.no_utilisateur\r\n " + 
			" WHERE a.date_debut_encheres <= CURRENT_TIMESTAMP and a.date_fin_encheres >= CURRENT_TIMESTAMP and a.no_utilisateur = ? \r\n " + 
			" group by no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, a.no_utilisateur, no_categorie ";

	private static final String AFFICHER_VENTES_NON_DEBUTEES = "SELECT no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, a.no_utilisateur, no_categorie\r\n " + 
			" from ARTICLES_VENDUS a inner join UTILISATEURS u on a.no_utilisateur = u.no_utilisateur\r\n " + 
			" WHERE a.date_debut_encheres >= CURRENT_TIMESTAMP and a.no_utilisateur = ? \r\n " + 
			" group by no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, a.no_utilisateur, no_categorie ";

	private static final String AFFICHER_VENTES_TERMINEES = "SELECT no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, a.no_utilisateur, no_categorie\r\n " + 
			" from ARTICLES_VENDUS a inner join UTILISATEURS u on a.no_utilisateur = u.no_utilisateur\r\n " + 
			" WHERE a.date_fin_encheres <= CURRENT_TIMESTAMP and a.no_utilisateur = ? \r\n " + 
			" group by no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, a.no_utilisateur, no_categorie\r\n " + 
			"";

	private static final String AFFICHER_ACHATS_EN_COURS = "SELECT * from ARTICLES_VENDUS where date_debut_encheres <= CURRENT_TIMESTAMP and date_fin_encheres >= CURRENT_TIMESTAMP";

	private static final String AFFICHER_ARTICLE_AVEC_ID = "SELECT * from ARTICLES_VENDUS where no_article = ?";

	private static final String AFFICHER_ENCHERES_REMPORTEES = "SELECT * from ARTICLES_VENDUS where date_fin_encheres <= CURRENT_TIMESTAMP";

	

	private static final String INSERT_VENTE = "INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, no_utilisateur, no_categorie) VALUES (?,?,?,?,?,?,?);";

	private static final String UPDATE_VENTE = "UPDATE ARTICLES_VENDUS SET nom_article = ?, description=?, date_debut_encheres= ?, date_fin_encheres = ?, prix_initial=?, no_utilisateur =?, no_categorie=? where no_article =?;";

	private static final String DELETE_VENTE = "DELETE from ARTICLES_VENDUS where no_article = ?";

	public ArticleVendu insertVente(ArticleVendu nouvelArticleVendu) throws DALException {

		ArticleVendu articleVendu = null;

// Obtenir une connexion
		Connection cnx = ConnectionProvider.getConnection();

// Obtient une objet de commande (Statement) = ordre SQL
		ResultSet rs = null;
		try {

// Paramétrer l'objet de commande

			Timestamp dateDebutTS = Timestamp.valueOf(nouvelArticleVendu.getDateDebutEncheres());
			Timestamp dateFinTS = Timestamp.valueOf(nouvelArticleVendu.getDateFinEncheres());

			PreparedStatement pStmt = cnx.prepareStatement(INSERT_VENTE, Statement.RETURN_GENERATED_KEYS);
			pStmt.setString(1, nouvelArticleVendu.getNomArticle());
			pStmt.setString(2, nouvelArticleVendu.getDescription());
			pStmt.setTimestamp(3, dateDebutTS);
			pStmt.setTimestamp(4, dateFinTS);
			pStmt.setInt(5, nouvelArticleVendu.getMiseAPrix());
			pStmt.setInt(6, nouvelArticleVendu.getNo_utilisateur());
			pStmt.setInt(7, nouvelArticleVendu.getCategorie());

// Execute l'ordre SQL

			pStmt.executeUpdate();
			rs = pStmt.getGeneratedKeys();

			if (rs.next()) {
				int idArticleInsere = rs.getInt(1);
				nouvelArticleVendu.setNoArticle(idArticleInsere);
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			if (rs != null) {

				try {
					cnx.close();
				} catch (SQLException e) {
// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return articleVendu;
	}

	@Override
	public void updateVente(ArticleVendu nouvelArticleVendu) throws DALException {
// Obtenir une connexion
		Connection cnx = ConnectionProvider.getConnection();

// Obtient une objet de commande (Statement) = ordre SQL
		try {

// Paramétrer l'objet de commande

			Timestamp dateDebutTS = Timestamp.valueOf(nouvelArticleVendu.getDateDebutEncheres());
			Timestamp dateFinTS = Timestamp.valueOf(nouvelArticleVendu.getDateFinEncheres());

			PreparedStatement pStmt = cnx.prepareStatement(UPDATE_VENTE);
			pStmt.setString(1, nouvelArticleVendu.getNomArticle());
			pStmt.setString(2, nouvelArticleVendu.getDescription());
			pStmt.setTimestamp(3, dateDebutTS);
			pStmt.setTimestamp(4, dateFinTS);
			pStmt.setInt(5, nouvelArticleVendu.getMiseAPrix());
			pStmt.setInt(6, nouvelArticleVendu.getNo_utilisateur());
			pStmt.setInt(7, nouvelArticleVendu.getCategorie());
			pStmt.setInt(8, nouvelArticleVendu.getNoArticle());

			pStmt.executeUpdate();

// Execute l'ordre SQL

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {

			try {
				cnx.close();
			} catch (SQLException e) {
// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	@Override
	public void deleteVente(int idArticle) throws DALException {
		Connection cnx = ConnectionProvider.getConnection();

// Obtient une objet de commande (Statement) = ordre SQL
		try {

// Paramétrer l'objet de commande

			PreparedStatement pStmt = cnx.prepareStatement(DELETE_VENTE);

			pStmt.setInt(1, idArticle);

// Execute l'ordre SQL
			pStmt.executeUpdate();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {

			try {
				cnx.close();
			} catch (SQLException e) {
// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public List<ArticleVendu> afficherVentesEnCours(int noUtilisateur) throws DALException {
		
		List<ArticleVendu> listeVenteAAfficher = new ArrayList<ArticleVendu>();

		ResultSet rs = null;

				// Obtenir une connexion
		Connection cnx = ConnectionProvider.getConnection();
		try {
			PreparedStatement pStmt = cnx.prepareStatement(AFFICHER_VENTES_EN_COURS);
			pStmt.setInt(1, noUtilisateur);
			
			
			rs = pStmt.executeQuery();

			while (rs.next()) {
				ArticleVendu article = mapAfficherVente(rs);				
					listeVenteAAfficher.add(article);
			}

		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {

				try {
					cnx.close();
				} catch (SQLException e) {
// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return listeVenteAAfficher;

	}

	public List<ArticleVendu> afficherVentesNonDebutees(int noUtilisateur) throws DALException {
		List<ArticleVendu> listeVenteAAfficher = new ArrayList<ArticleVendu>();

		ResultSet rs = null;

				// Obtenir une connexion
		Connection cnx = ConnectionProvider.getConnection();
		try {
			PreparedStatement pStmt = cnx.prepareStatement(AFFICHER_VENTES_NON_DEBUTEES);
			pStmt.setInt(1, noUtilisateur);
			
			rs = pStmt.executeQuery();

			while (rs.next()) {
				ArticleVendu article = mapAfficherVente(rs);
					listeVenteAAfficher.add(article);
			}

		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {

				try {
					cnx.close();
				} catch (SQLException e) {
// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return listeVenteAAfficher;
	}

	public List<ArticleVendu> afficherVentesTerminees(int noUtilisateur) throws DALException {
		
		List<ArticleVendu> listeVenteAAfficher = new ArrayList<ArticleVendu>();

		ResultSet rs = null;

				// Obtenir une connexion
		Connection cnx = ConnectionProvider.getConnection();
		try {
			PreparedStatement pStmt = cnx.prepareStatement(AFFICHER_VENTES_TERMINEES);
			pStmt.setInt(1, noUtilisateur);

			rs = pStmt.executeQuery();

			while (rs.next()) {
				ArticleVendu article = mapAfficherVente(rs);
					listeVenteAAfficher.add(article);
			}

		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {

				try {
					cnx.close();
				} catch (SQLException e) {
// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return listeVenteAAfficher;
	}
		
	

	

	@Override
	public List<ArticleVendu> afficherAchatsEnCours() throws DALException {

		List<ArticleVendu> listeAchatsAAfficher = new ArrayList<ArticleVendu>();

		ResultSet rs = null;
// Obtenir une connexion
		Connection cnx = ConnectionProvider.getConnection();
		try {
			Statement pStmt = cnx.createStatement();

			rs = pStmt.executeQuery(AFFICHER_ACHATS_EN_COURS);

			while (rs.next()) {

				ArticleVendu article = mapAfficherVente(rs);

				listeAchatsAAfficher.add(article);
			}

		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {

				try {
					cnx.close();
				} catch (SQLException e) {
// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return listeAchatsAAfficher;
	}

// @Override
	public List<ArticleVendu> afficherEncheresRemportees(String pseudo) throws DALException {

		List<ArticleVendu> listeAchatsAAfficher = new ArrayList<ArticleVendu>();
		int noArticle = 0;
		int noAcheteur = 0;
		Enchere nouvelleEnchere = null;
		ResultSet rs = null;
// Obtenir une connexion
		Connection cnx = ConnectionProvider.getConnection();
		try {
			Statement pStmt = cnx.createStatement();

			rs = pStmt.executeQuery(AFFICHER_ENCHERES_REMPORTEES);

			while (rs.next()) {

				ArticleVendu article = mapAfficherVente(rs);
				noArticle = article.getNoArticle();
				
					if (EnchereManager.getInstance().trouverMeilleurEncherisseur(noArticle) != null) {
						
						nouvelleEnchere = EnchereManager.getInstance().trouverMeilleurEncherisseur(noArticle);
						
						noAcheteur = nouvelleEnchere.getNoUtilisateur();
						System.out.println(pseudo);
						
						if (noAcheteur == (UtilisateurManager.getInstance().afficherProfil(pseudo).getNoUtilisateur())) {
							listeAchatsAAfficher.add(article);
						}
					}
				
			}

		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BLLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			if (rs != null) {

				try {
					cnx.close();
				} catch (SQLException e) {
// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return listeAchatsAAfficher;
	}

	@Override
	public List<ArticleVendu> afficherMesEncheres(String pseudo) throws DALException {

		List<ArticleVendu> listeAchatsAAfficher = new ArrayList<ArticleVendu>();
		int noAcheteur = 0;
		ResultSet rs = null;
// Obtenir une connexion
		Connection cnx = ConnectionProvider.getConnection();
		try {
			Statement pStmt = cnx.createStatement();

			rs = pStmt.executeQuery(AFFICHER_ACHATS_EN_COURS); 

			while (rs.next()) {

				ArticleVendu article = mapAfficherVente(rs);
				
				try {
					if (noAcheteur == (UtilisateurManager.getInstance().afficherProfil(pseudo).getNoUtilisateur())) {
						listeAchatsAAfficher.add(article);
					}
				} catch (BLLException e) {
// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} catch (SQLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {

				try {
					cnx.close();
				} catch (SQLException e) {
// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return listeAchatsAAfficher;
	}

	
//	--------------------------------------------------------------
	
	
	
	
//	----------------------------------------------------------------
	

	public ArticleVendu mapAfficherVente(ResultSet rs) throws SQLException {
		ArticleVendu articleVendu = null;
		String pseudo = null;
		int meilleurEnchere = 0;
		int no_Article = rs.getInt("no_article");
		String nomArticle = rs.getString("nom_article");
		String description = rs.getString("description");
		LocalDateTime dateDebutEncheres = rs.getTimestamp(4).toLocalDateTime();
		LocalDateTime dateFinEncheres = rs.getTimestamp(5).toLocalDateTime();
		int miseAPrix = rs.getInt("prix_initial");
		int prixVente = rs.getInt("prix_vente");
		int categorie = rs.getInt("no_categorie");
		int no_utilisateur = rs.getInt("no_utilisateur");
		Enchere enchere = null;
		try {
			enchere = EnchereManager.getInstance().trouverMeilleurEncherisseur(no_Article);
		} catch (BLLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(enchere != null) {
			
			 meilleurEnchere = enchere.getMontant_enchere();
		}
		try {
			pseudo = UtilisateurManager.getInstance().afficherProfilAvecId(no_utilisateur).getPseudo();
		} catch (BLLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}

		articleVendu = new ArticleVendu(no_Article, nomArticle, description, dateDebutEncheres, dateFinEncheres,
				miseAPrix, prixVente, no_utilisateur, categorie,  pseudo,meilleurEnchere);

		return articleVendu;
	}



	@Override
	public ArticleVendu selectArticleById(int noArticle) throws DALException {
		ArticleVendu article = null;
		// Obtenir une connexion
		Connection cnx = ConnectionProvider.getConnection();

		// Obtient une objet de commande (Statement) = ordre SQL
		ResultSet rs = null;
		try {

			PreparedStatement pStmt = cnx.prepareStatement(AFFICHER_ARTICLE_AVEC_ID);
			pStmt.setInt(1, noArticle);

			rs = pStmt.executeQuery();

			if (rs.next()) {
				article = mapAfficherVente(rs);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			if (rs != null) {

				try {
					cnx.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return article;
	}

}