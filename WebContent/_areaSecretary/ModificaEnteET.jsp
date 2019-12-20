<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="model.DAO.EnteConvenzionatoDAO,model.EnteConvenzionato, controller.CheckSession, java.util.*"%>
	
<%
	//Autoriempimento form
	int i = Integer.valueOf(request.getParameter("ente"));
	System.out.println(i);
	EnteConvenzionatoDAO entedao = new EnteConvenzionatoDAO();
	ArrayList<EnteConvenzionato> listaEnti = entedao.allEnte(); //(ArrayList<EnteConvenzionato>) request.getAttribute("listaEnti");
	//Page Folder
	String pageName = "ModificaEnteET.jsp";
	String pageFolder = "_areaSecretary";
	//CheckSession
	String Segreteria = " ";
	Segreteria = (String) session.getAttribute("Segreteria");
	//System.out.println("Segreteria: " + Segreteria);
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
										<h2 class="text-center">Modifica Ente Convenzionato</h2>
										<p class="text-center">Compila tutti i campi per
											modificare un nuovo ente.</p>
									</div>
									<form id="signUp" action="../ServletModificaEnteET"
										method="post">
										<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
											<label for="name">Nome Ente</label> <input type="text"
												class="form-control" id="name" name="name"
												placeholder="Nome Ente" value=<%=listaEnti.get(i).getName() %> minlength="1" maxlength="64"
												required>
										</div>
										<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
											<label for="partitaIva">Partita IVA</label> <input type="tel"
												oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"
												class="form-control" placeholder="Partita IVA" value=<%=listaEnti.get(i).getPartitaIva() %>
												name="partitaIva" id="partitaIva" size="11" required>
										</div>
										<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
											<label for="email">Email</label> <input type="email"
												class="form-control" id="email" name="email"
												placeholder="Email" value=<%=listaEnti.get(i).getEmail() %> minlength="1" maxlength="64" required>
										</div>
										<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
											<label for="sede">Sede</label> <input type="text"
												class="form-control" id="sede" name="sede"
												placeholder="Sede" value=<%=listaEnti.get(i).getSede() %> minlength="1" maxlength="64" required>
										</div>
										<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
											<label for="telefono">Numero di telefono</label> <input
												type="tel"
												oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"
												class="form-control" placeholder="Numero di Telefono" value=<%=listaEnti.get(i).getTelefono() %>
												name="telefono" id="telefono" size="10" required>
										</div>
										<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
											<label for="dipendenti">Numero Dipendenti</label> <input
												type="text" class="form-control"
												placeholder="Numero di Dipendenti" value=<%=listaEnti.get(i).getDipendenti() %> name="dipendenti"
												id="dipendenti" required>
										</div>
										<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
											<label for="rappresentante">Nome Rappresentante</label> <input
												type="text" class="form-control" id="rappresentante"
												name="rappresentante" placeholder="Nome Rappresentante" value=<%=listaEnti.get(i).getRappresentante() %>
												minlength="1" maxlength="64" required>
										</div>
										<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
											<label for="dataDiNascita">Data di Nascita del
												Rappresentante</label> <input type="text" class="form-control"
												placeholder="Data di Nascita" value=<%=listaEnti.get(i).getDataDiNascita() %> name="dataDiNascita"
												id="dataDiNascita" required>
										</div>
										<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
											<label for="dotRiferimento">Professore di Riferimento</label>
											<input type="text" class="form-control" id="dotRiferimento"
												name="dotRiferimento"
												placeholder="Professore di Riferimento" value=<%=listaEnti.get(i).getDotRiferimento() %> minlength="1"
												maxlength="64" required>
										</div>
										<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
											<label for="referente">Referente Tirocini</label> <input
												type="text" class="form-control"
												placeholder="Referente Tirocini" value=<%=listaEnti.get(i).getReferente() %> minlength="1"
												maxlength="64" name="referente" id="referente" required>
										</div>
										<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
											<label for="descrizioneAttivita">Descrizione Attivit&agrave;</label>
											<input type="text" class="form-control"
												placeholder="Descrizione delle Attivit&agrave;" value=<%=listaEnti.get(i).getDescrizioneAttivita() %> minlength="1"
												maxlength="256" name="descrizioneAttivita"
												id="descrizioneAttivita" required>
										</div>
										<div
											class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
											<button type="submit" class="btn btn-primary btn-submit">Modifica
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

	<jsp:include page="/partials/includes.jsp" />

</body>
</html>