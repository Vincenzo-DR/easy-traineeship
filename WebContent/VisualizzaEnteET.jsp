<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, controller.ServletListaEnteET, model.EnteConvenzionato, controller.CheckSession" %>
<%
	String pageName = "VisualizzaEnteET.jsp";
	String pageFolder = "";
	

	CheckSession ck = new CheckSession(pageFolder, pageName, request.getSession());
	


	ArrayList<EnteConvenzionato> listaEnti=new ArrayList<EnteConvenzionato>();
	listaEnti=(ArrayList<EnteConvenzionato>)request.getAttribute("listaEnti");
	
	if(listaEnti==null)
	{
        RequestDispatcher dispatcher = request.getRequestDispatcher("ServletListaEnteET");
        dispatcher.forward(request, response);
    }

	
	
%>

<!DOCTYPE html>
<html>
<head>
<jsp:include page="/partials/head.jsp" />
</head>
<body onLoad="showData()">
	<div class="page-wrapper">
	
		<jsp:include page="/partials/header.jsp">
			<jsp:param name="pageName" value="<%= pageName %>" />
			<jsp:param name="pageFolder" value="<%= pageFolder %>" />
		</jsp:include>
		
		<div class="sidebar-page-container basePage viewRequestStudent">
			<div class="auto-container">
				<div class="row clearfix">
					<div class="content-side col-lg-12 col-md-12 col-sm-12 col-xs-12">
						<div class="content">
							<div class="news-block-seven">
								<%
								if(listaEnti!=null)
								{
								%>
									<table id="TabellaEnteTable" class="display data-results table table-striped table-hover table-bordered">
	
										<thead>
											<tr align="center">
												<th class="text-center" align="center">Nome Ente</th>
												<th class="text-center" align="center">Partita Iva</th>
												<th class="text-center" align="center">Sede</th>
												<th class="text-center" align="center">Dipendenti</th>
												<th class="text-center" align="center">Attivit&agrave;</th>
												<th class="text-center" align="center">Referente</th>
												<th class="text-center" align="center">E-Mail</th>
												<th class="text-center" align="center">Telefono</th>
												
											</tr>
										</thead>
										<tbody  >
											<%
											for( int i = 0; i < listaEnti.size(); i++)
											{ %>
												<tr role='row' >
													<td class='text-center'><%=listaEnti.get(i).getName()%></td>
													<td class='text-center'><%=listaEnti.get(i).getPartitaIva()%></td>
													<td class='text-center'><%=listaEnti.get(i).getSede()%></td>
													<td class='text-center'><%=listaEnti.get(i).getDipendenti()%></td>
													<td class='text-center'><%=listaEnti.get(i).getDescrizioneAttivita()%></td>
													<td class='text-center'><%=listaEnti.get(i).getReferente()%></td>
													<td class='text-center'><%=listaEnti.get(i).getEmail()%></td>
													<td class='text-center'><%=listaEnti.get(i).getTelefono()%></td>
												</tr>
											<%
											} %>
										</tbody>
									</table>
								<%}
								if(listaEnti==null)
								{
									%>
									<center><h2>Lista Ente Vuota</h2></center>
									<%
								}
								%>
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
	
	<script>
			jQuery(document).ready(function($){
				$('#TabellaEnteTable').DataTable( {
			        "order": [[ 0, "desc" ]],
			        "lengthMenu": [[10, -1], [10, "Tutti"]],
			        "autoWidth": false,
			        "bAutoWidth": false,
			        "language": {
						    "sEmptyTable":     "Nessuna Richiesta Presente",
						    "sInfo":           "Vista da _START_ a _END_ di _TOTAL_ elementi",
						    "sInfoEmpty":      "Vista da 0 a 0 di 0 elementi",
						    "sInfoFiltered":   "(filtrati da _MAX_ elementi totali)",
						    "sInfoPostFix":    "",
						    "sInfoThousands":  ".",
						    "sLengthMenu":     "Visualizza _MENU_ elementi",
						    "sLoadingRecords": "Caricamento...",
						    "sProcessing":     "Elaborazione...",
						    "sSearch":         "Cerca:",
						    "sZeroRecords":    "La ricerca non ha portato alcun risultato.",
						    "oPaginate": {
						        "sFirst":      "Inizio",
						        "sPrevious":   '<i class="fa fa-caret-left"></i>',
						        "sNext":       '<i class="fa fa-caret-right"></i>',
						        "sLast":       "Fine"
						    },
						    "oAria": {
						        "sSortAscending":  ": attiva per ordinare la colonna in ordine crescente",
						        "sSortDescending": ": attiva per ordinare la colonna in ordine decrescente"
						    }

			        }        
			    } );
			});
		</script>
	
</body>
</html>