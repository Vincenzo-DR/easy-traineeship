<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	 //Controllo autenticazione tramite parametro in sessione (1 = Segreteria).
	String userET = (String) request.getSession().getAttribute("userET");
	if ((userET == null) || (!userET.equals("1"))) {
		response.sendRedirect("login.jsp");
		return;
	}
	//Page Folder
	String pageName = "RegistrazioneEnteET.jsp";
	String pageFolder = "_areaSecretary";
	//CheckSession
	String Segreteria = " ";
	Segreteria = (String) session.getAttribute("Segreteria");
	//Stringa della modifica avvenuta
	String mess = (String) request.getAttribute("mess");
	
%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/partials/head.jsp" />
</head>

<body onLoad="">
	<div class="page-wrapper" id="registrazioneEnte">

		<!-- Preloader -->
		<!-- <div class="preloader"></div>  -->


		<jsp:include page="/partials/header.jsp">
			<jsp:param name="pageName" value="<%=pageName%>" />
			<jsp:param name="pageFolder" value="<%=pageFolder%>" />
		</jsp:include>


		<div class="sidebar-page-container basePage signUpPage">
			<div class="auto-container">
				<div class="row clearfix">
					<div class="content-side col-lg-12 col-md-12 col-sm-12 col-xs-12">
						<div class="content">
							<div class="news-block-seven">
								<div
									class="col-lg-6 col-md-6 col-sm-12 col-xs-12 signUp-container">
									<div class="panel">
										<h2 class="text-center">Registrazione Ente Convenzionato</h2>
										<p class="text-center">Compila tutti i campi per
											registrare un nuovo ente.</p>
									</div>
									<form id="signUp" action="../ServletRegistrazioneEnteET"
										method="post">
										<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
											<label for="name">Nome Ente</label> <input type="text"
												class="form-control" id="name" name="name"
												placeholder="Nome Ente" minlength="1" maxlength="64"
												required pattern="[0-9a-zA-Z]{1,64}" title="Formato del nome non corretto.">
										</div>
										<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
											<label for="partitaIva">Partita IVA</label> <input type="tel"
												oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"
												class="form-control" placeholder="Partita IVA"
												name="partitaIva" id="partitaIva" size="11" required pattern="[0-9]{11}" title="Formato della partita iva non corretto.">
										</div>
										<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
											<label for="email">Email</label> <input type="email"
												class="form-control" id="email" name="email"
												placeholder="Email" minlength="1" maxlength="64" title="Formato dell'Email non corretto."  required>
										</div>
										<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
											<label for="sede">Sede</label> <input type="text"
												class="form-control" id="sede" name="sede"
												placeholder="Sede" minlength="1" maxlength="64" required pattern="[A-Z a-z 0-9]{1,64}" title="Formato della Sede non corretto.">
										</div>
										<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
											<label for="telefono">Numero di telefono</label> <input
												type="tel"
												oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"
												class="form-control" placeholder="Numero di Telefono"
												name="telefono" id="telefono" size="10" required pattern="[0-9]{10}" title="Formato del telefono non corretto.">
										</div>
										<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
											<label for="dipendenti">Numero Dipendenti</label> <input
												type="text" class="form-control"
												placeholder="Numero di Dipendenti" name="dipendenti"
												id="dipendenti" required pattern="[0-9]{1,64}" title="Formato del numero di dipendenti non corretto.">
										</div>
										<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
											<label for="rappresentante">Nome Rappresentante</label> <input
												type="text" class="form-control" id="rappresentante"
												name="rappresentante" placeholder="Nome Rappresentante"
												minlength="1" maxlength="64" required pattern="[a-z A-Z]{1,64}" title="Formato del Rappresentante non corretto.">
										</div>
										<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
											<label for="dataDiNascita">Data di Nascita del
												Rappresentante</label> <input type="text" class="form-control"
												placeholder="Data di Nascita" name="dataDiNascita"
												id="dataDiNascita" required pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])/(0[1-9]|1[012])/[0-9]{4}" title="Formato della data di nascita non corretto.">
										</div>
										<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
											<label for="dotRiferimento">Professore di Riferimento</label>
											<input type="text" class="form-control" id="dotRiferimento"
												name="dotRiferimento"
												placeholder="Professore di Riferimento" minlength="1"
												maxlength="64" required pattern="[a-z A-Z]{1,64}" title="Formato del Professore di Riferimento non corretto.">
										</div>
										<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
											<label for="referente">Referente Tirocini</label> <input
												type="text" class="form-control"
												placeholder="Referente Tirocini" minlength="1"
												maxlength="64" name="referente" id="referente" required pattern="[a-z A-Z]{1,64}" title="Formato del referente non corretto.">
										</div>
										<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
											<label for="descrizioneAttivita">Descrizione Attivit&agrave;</label>
											<input type="text" class="form-control"
												placeholder="Descrizione delle Attivit&agrave;" minlength="1"
												maxlength="256" name="descrizioneAttivita"
												id="descrizioneAttivita"  title="Formato della descrizione attivit� non corretto." required>
										</div>
										<div
											class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
											<button type="submit" class="btn btn-primary btn-submit">Registra
												Ente</button>
										</div>
										<div class="clearfix"></div>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="/partials/footer.jsp" />
	</div>
	<!--End pagewrapper-->

	<!--!!!!!!!!CAUSA ERRORI!!!!!!!!!!!-->
	<jsp:include page="/partials/includes.jsp" />

	<script>
		/*Form Input Values*/
		const form = document.forms["registrazioneEnte"]
		const name = document.forms["registrazioneEnte"]["name"].value;
		const partitaIva = document.forms["registrazioneEnte"]["partitaIva"].value;
		const email = document.forms["registrazioneEnte"]["email"].value;
		const sede = document.forms["registrazioneEnte"]["sede"].value;
		const telefono = document.forms["registrazioneEnte"]["telefono"].value;
		const dipendenti = document.forms["registrazioneEnte"]["dipendenti"].value;
		const rappresentante = document.forms["registrazioneEnte"]["rappresentante"].value;
		const dataDiNascita = document.forms["registrazioneEnte"]["dataDiNascita"].value;
		const dotRiferimento = document.forms["registrazioneEnte"]["dotRiferimento"].value;
		const referente = document.forms["registrazioneEnte"]["referente"].value;
		const descrizioneAttivita = document.forms["registrazioneEnte"]["descrizioneAttivita"];
		console.log("descrizioneAttivita")
		/*REGEX Values*/
		const alphaNumRGX = /^[a-z A-Z 0-9]$/
		const alphRGX = /^[a-z A-Z]$/
		const dateRGX = /^[1-9]{2}/ [1-9]{2}/[0-9]{4}$/
		const enumRGX = /^[0-9]$/
		const mailRGX = /^[A-z0-9\.\+_-] +@[A-z0-9\._-]+\.[A-z]{2,6}$/
		const numRGX = /^[0-9]{10}$/
		/*This function validate form inputs*/
		function validate() {
			/*LENGTH VALIDATION*/
			if (name.length <= 0 || name.length > 64) // Check Length
			{
				toastr.error("Lunghezza non rispettata")
				return false;
			}
			if (partitaIva.length <= 0 || partitaIva.length > 64) // Check Length
			{
				toastr.error("Lunghezza non rispettata")
				return false;
			}
			if (email.length <= 0 || email.length > 64) // Check Length
			{
				toastr.error("Lunghezza non rispettata")
				return false;
			}
			if (sede.length <= 0 || sede.length > 64) // Check Length
			{
				toastr.error("Lunghezza non rispettata")
				return false;
			}
			if (telefono.length <= 0 || telefono.length > 64) // Check Length
			{
				toastr.error("Lunghezza non rispettata")
				return false;
			}
			if (dipendenti.length <= 0 || dipendenti.length > 64) // Check Length
			{
				toastr.error("Lunghezza non rispettata")
				return false;
			}
			if (rappresentante.length <= 0 || rappresentante.length > 64) // Check Length
			{
				toastr.error("Lunghezza non rispettata")
				return false;
			}
			if (dataDiNascita.length <= 0 || dataDiNascita.length > 64) // Check Length
			{
				toastr.error("Lunghezza non rispettata")
				return false;
			}
			if (dotRiferimento.length <= 0 || dotRiferimento.length > 64) // Check Length
			{
				toastr.error("Lunghezza non rispettata")
				return false;
			}
			if (referente.length <= 0 || referente.length > 64) // Check Length
			{
				toastr.error("Lunghezza non rispettata")
				return false;
			}
			if (descrizioneAttivita.length <= 0 || descrizioneAttivita.length > 64) // Check Length
			{
				toastr.error("Lunghezza non rispettata")
				return false;
			}
			/*FORMAT VALIDATION*/
			if (!alphRGX.test(name)) // Check Format
			{
				toastr.error("Formato non rispettato")
				return false;
			}
			if (!numRGX.test(partitaIva)) // Check Format
			{
				toastr.error("Formato non rispettato")
				return false;
			}
			if (!mailRGX.test(email)) // Check Format
			{
				toastr.error("Formato non rispettato")
				return false;
			}
			if (!alphRGX.test(sede)) // Check Format
			{
				toastr.error("Formato non rispettato")
				return false;
			}
			if (!numRGX.test(telefono)) // Check Format
			{
				toastr.error("Formato non rispettato")
				return false;
			}
			if (!enumRGX.test(dipendenti)) // Check Format
			{
				toastr.error("Formato non rispettato")
				return false;
			}
			if (!alphRGX.test(rappresentante)) // Check Format
			{
				toastr.error("Formato non rispettato")
				return false;
			}
			if (!dateRGX.test(dataDiNascita)) // Check Format
			{
				toastr.error("Formato non rispettato")
				return false;
			}
			if (!alphRGX.test(dotRiferimento)) // Check Format
			{
				toastr.error("Formato non rispettato")
				return false;
			}
			if (!alphRGX.test(referente)) // Check Format
			{
				toastr.error("Formato non rispettato")
				return false;
			}
			if (!alphaNumRGX.test(descrizioneAttivita)) // Check Format
			{
				toastr.error("Formato non rispettato")
				return false;
			}

			return true
		}
	</script>
</body>
</html>
