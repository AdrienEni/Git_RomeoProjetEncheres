<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<meta http-equiv="refresh" content="300;url=/RomeoProjetEncheres/accueil" />

<title>Vendre un article</title>

<link rel="shortcut icon" href="assets/images/gt_favicon.png">

<link rel="stylesheet" media="screen"
	href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,700">
<link rel="stylesheet" href="assets/css/bootstrap.min.css">
<link rel="stylesheet" href="assets/css/font-awesome.min.css">

<!-- Custom styles for our template -->
<link rel="stylesheet" href="assets/css/bootstrap-theme.css"
	media="screen">
<link rel="stylesheet" href="assets/css/main.css">

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
	<script src="assets/js/html5shiv.js"></script>
	<script src="assets/js/respond.min.js"></script>
	<![endif]-->
	<!-- ----------------------AJOUT NEW SUPER NAVBAR TOP------------------------- -->
    <link rel="stylesheet" type="text/css" href="assets/css/style.css">
   <!-- ----------------------AJOUT NEW SUPER NAVBAR BOT------------------------- -->
	
	
<script>
	$(function() {
		$('#datetimepicker1').datetimepicker();
	});
</script>
<style>
</style>
</head>

<body>


			<!-- ----------------------AJOUT NEW SUPER NAVBAR TOP------------------------- -->

<div class="container-back">
	<!-- Fixed navbar -->
	<div class="navbar navbar-inverse navbar-fixed-top headroom">
		<div class="container">
			<div class="navbar-header">
				<!-- Button for smallest screens -->
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="/RomeoProjetEncheres/afficherConnected">
				<div class="logoRomeo">
				<img src="assets/images/logo.png" >
				</div>
				</a>
			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav "> <!-- pull-right -->
				
    <div class="navigation">
        <ul>
            <li class="list active">
                <a href="/RomeoProjetEncheres/afficherConnected">
                    <span class="icon">
                        <ion-icon name="home-outline"></ion-icon>
                    </span>
                    <span class="text">Enchères</span>
                </a>
            </li>
 			<li class="list">
                <a href="/RomeoProjetEncheres/vendre">
                    <span class="icon">
                        <ion-icon name="add-circle-outline"></ion-icon>
                    </span>
                    <span class="text">Ventes</span>
                </a>
            </li>
            <li class="list">
                <a href="/RomeoProjetEncheres/favoris.jsp">
                    <span class="icon">
                        <ion-icon name="star-outline"></ion-icon>
                    </span>
                    <span class="text">Favoris</span>
                </a>
            </li>

            <li class="list">
                <a href="/RomeoProjetEncheres/modifier">
                    <span class="icon">
                        <ion-icon name="person-outline"></ion-icon>
                    </span>
                    <span class="text">Mon Profil</span>
                </a>
            </li>
            <li class="list">
                <a href="/RomeoProjetEncheres/accueil">
                    <span class="icon"><ion-icon name="log-out-outline"></ion-icon>
                        
                    </span>
                    <span class="text">Deconnexion</span>
                </a>
            </li>
            
            <div class="indicator"></div>
        </ul>
    </div>
</ul>
	</div>
			<!--/.nav-collapse -->
		</div>
	</div>
	<!-- /.navbar -->
   <!-- ----------------------AJOUT NEW SUPER NAVBAR BOT------------------------- -->

	<header id="head" class="secondary"></header>

	<!-- container -->
	<div class="container">

		<br><ol class="breadcrumb">
			<li><a href="/RomeoProjetEncheres/accueil">Accueil</a></li>
			<li class="active">Donnez une seconde vie à vos objets</li>
		</ol><br>
		
		

	<!-- /container AVEC BREADCRUMB -->
		</div>

		<!-- container -->
<div class="container">
			<div class="panel panel-default">
				<div class="panel-body">
	<div class="row">
		<div class="col-md-12">
			<h3 class="text-center">
				Nouvelle Vente
			</h3>
		<c:if test="${messageErreur != null && messageErreur == 1}">
			<p class="text-center mdpIncorrect"> La mise à prix doit être supérieure à 0 et inférieure à beaucoup plus que ton compte en banque sauf si t'es Jeff Bezos.</p>
		</c:if>
		<c:if test="${messageErreur != null && messageErreur == 2}">
			<p class="text-center mdpIncorrect"> Vous ne pouvez prévoir une date de fin d'enchère déjà passée. </p>
		</c:if>
		<c:if test="${messageErreur != null && messageErreur == 3}">
			<p class="text-center mdpIncorrect"> Vous ne pouvez prévoir une date de début d'enchère déjà passée. </p>
		</c:if>
		<c:if test="${messageErreur != null && messageErreur == 4}">
			<p class="text-center mdpIncorrect"> Vous devez prévoir une description (inf. à 300 caractères). </p>
		</c:if>
		<c:if test="${messageErreur != null && messageErreur == 5}">
			<p class="text-center mdpIncorrect"> Vous devez prévoir un nom d'article (inf. à 30 caractères). </p>
		</c:if>
		<c:if test="${messageErreur != null && messageErreur == 6}">
			<p class="text-center mdpIncorrect"> La date de fin d'enchère doit être postérieure à son début. </p>
		</c:if>
		</div>
	</div>
	
	
		
	<div class="row">
		<div class="col-md-2">
			<img alt="" src="" />
		</div>
		
		
		
		<div class="col-md-8">
			<form name="mettrenvente" role="form" method="post">
		
	
			
				<div class="form-group">
					<label for="article">Article</label> <input type="text"
						class="form-control" id="article" name="article" placeholder="Article" required>
				</div>

				<div class="form-group">
					<label for="">Description de l'article</label>
					<textarea class="form-control" id="description" rows="3" name="description"
						placeholder="Description" required></textarea>
				</div>

				<div class="form-group">
					<label for=""> Catégorie</label> <select class="form-control"
						id="exampleFormControlSelect1" name="categorie" required>
						<option></option>
						<option>Informatique</option>
						<option>Ameublement</option>
						<option>Vetement</option>
						<option>Sport et Loisirs</option>
					</select>
				</div>

				<div class="form-group">
					<label for=""> Choisissez une photo </label> <input type="file"
						class="form-control-file" id="uploadphoto">
				</div>

				<label>Début de l'enchère : </label> <input type="datetime-local"
					 placeholder="" name="debutEnchere" required> 
					<br>
					<label>Fin
					de l'enchère : </label> <input type="datetime-local" name="finEnchere"
					placeholder="" required>
					
		<br>
				<label> Mise à prix :  </label><input type="number" name="miseAPrix"
					placeholder="" required>	
					<br>
					<br>
					
					
			<div class="row">
				<div class="button text-center m-10">		
				<a href="/RomeoProjetEncheres/vendre"><button class="btn btn-action" formmethod="post" name="vendre" value="true">Mettre en vente mon article</button></a>	
				<!-- TODO  REMPLIR FORM ET NAME avec? Adrien -->
			</div>	
			</div>
				
			
			</form>
			
			<fieldset class="p-5">
					<legend> Retrait </legend>
					
					<div class="row">
  						<div class="col-md-2"><label for="article"> Rue </label></div>
  						<div class="col-md-4 col-md-offset-1"><input type="text"
						class="form-control" id="article" value="${rue}" readonly="readonly"></div>
					</div> 
					<br>
					<div class="row">
  						<div class="col-md-2"><label for="article"> Code Postal </label></div>
  						<div class="col-md-4 col-md-offset-1"><input type="text"
						class="form-control" id="article" value="${codePostal}" readonly="readonly"></div>
					</div>   
					<br>
					<div class="row">
  						<div class="col-md-2"><label for="article"> Ville </label></div>
  						<div class="col-md-4 col-md-offset-1"><input type="text"
						class="form-control" id="article" value="${ville}" readonly="readonly"></div>
					</div>  
				</fieldset>
				
		</div>
	</div>
	<br><br><br>
</div>
</div>
	</div>	
		
		
		
		
		


		<!-- /container -->


	<footer id="footer" class="top-space">

		<div class="footer1">
			<div class="container">
				<div class="row">

					<div class="col-md-3 widget">
						<h3 class="widget-title">Contact</h3>
						<div class="widget-body">
							<p>
								02 00 00 00 00<br> <a href="mailto:#">losna@mail.com</a><br>
								<br> 3 Rue Michael Faraday, Saint-Herblain, FRANCE
							</p>
						</div>
					</div>

					<div class="col-md-3 widget">
						<h3 class="widget-title">Suivez-nous</h3>
						<div class="widget-body">
							<p class="follow-me-icons clearfix">
								<a href=""><i class="fa fa-twitter fa-2"></i></a> <a href=""><i
									class="fa fa-dribbble fa-2"></i></a> <a href=""><i
									class="fa fa-github fa-2"></i></a> <a href=""><i
									class="fa fa-facebook fa-2"></i></a>
							</p>
						</div>
					</div>

					<div class="col-md-6 widget">
						<h3 class="widget-title">Association: "Les objets sont nos amis"</h3>
						<div class="widget-body">
							<p>Utiliser notre site, c'est partager nos valeurs!</p>
							<p>&nbsp;&nbsp;- Nos idées neuves</p>
							<p>&nbsp;&nbsp;- Une appétence grandissante pour la réutilisation et le recyclage</p>
							<p>&nbsp;&nbsp;- Encourager la réutilisation, donner une seconde vie aux objets en facilitant les échanges</p>
							<p>&nbsp;&nbsp;- Permettre l'échange du plus grand nombre d'objets possible</p>
							<br>

						</div>
					</div>

				</div>
				<!-- /row of widgets -->
			</div>
		</div>

	</footer>





	<!-- JavaScript libs are placed at the end of the document so the pages load faster -->
	
	    <script>
        const list = document.querySelectorAll('.list');
        function activeLink() {
            list.forEach((item) => item.classList.remove('active'));
            this.classList.add('active');
        }
        list.forEach((item) => item.addEventListener('mouseover', activeLink));


    </script>
    <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
		<script
			src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
		<script
			src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
		<script src="assets/js/headroom.min.js"></script>
		<script src="assets/js/jQuery.headroom.min.js"></script>
		<script src="assets/js/template.js"></script>
	</div>
</body>
</html>