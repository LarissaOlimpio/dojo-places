<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Locais</title>
    <link rel="stylesheet" type="text/css" href="/assets/css/tableLocals/table.css">


</head>
<body>
<div class="container">
    <h2>Locais</h2>
    <form action="<c:url value="/local/create" />" method="get">
        <input  class="button newLocal" type="submit" value="Cadastrar Novo Local" >
    </form>
</div>

<div>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Código</th>
        <th>Nome</th>
        <th>Data de Criação</th>
        <th>Última atualização</th>
        <th>Ações</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="localResponseDTOList" items="${localResponseDTOList}">
        <tr>
            <td>${localResponseDTOList.id}</td>
            <td>${localResponseDTOList.code}</td>
            <td>${localResponseDTOList.name}</td>
            <td>${localResponseDTOList.creationDate}</td>
            <td>${localResponseDTOList.daysSinceLastUpdate}</td>
            <td class="actions">
                <div class="div-button">
                    <form action="/local/update/${localResponseDTOList.id}" method="get">
                        <input class="button edit" type="submit" value="Editar">
                    </form>
                    <form action="/local/delete/${localResponseDTOList.id}" method="post" onsubmit="return confirm('Tem certeza de que deseja deletar este local?');">
                        <input class="button delete" type="submit" value="Deletar">
                    </form>
                </div>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</div>
</body>
</html>
