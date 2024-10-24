<%@page contentType="text/html" pageEncoding="UTF-8" %> <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="Dự án laptopshop" />
        <title>Dashboard</title>
        <link href="/css/styles.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    </head>

    <body class="sb-nav-fixed">
        <jsp:include page="../layout/header.jsp" />
        <div id="layoutSidenav">
            <jsp:include page="../layout/sidebar.jsp" />
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid px-4">
                        <h1 class="mt-4">Manage Orders</h1>
                        <ol class="breadcrumb mb-4">
                            <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
                            <li class="breadcrumb-item active">Orders</li>
                        </ol>
                        <div class="mt-5">
                            <div class="row">
                                <div class="col-12 mx-auto">
                                    <div class="d-flex justify-content-between">
                                        <h3>Table Products</h3>
                                    </div>

                                    <hr />

                                    <table class="table table-bordered table-hover">
                                        <thead>
                                            <tr>
                                                <th scope="col">ID</th>
                                                <th scope="col">Total Price</th>
                                                <th scope="col">User</th>
                                                <th scope="col">Status</th>
                                                <th scope="col">Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="order" items="${orders}">
                                                <tr>
                                                    <td>${order.id}</td>
                                                    <td>
                                                        <fmt:formatNumber value="${order.totalPrice}" type="number" /> đ
                                                    </td>
                                                    <td>${order.user.role.name}</td>
                                                    <td>${order.status}</td>
                                                    <td>
                                                        <a class="btn btn-success" href="/admin/order/${order.id}"
                                                            >View</a
                                                        >
                                                        <a
                                                            class="btn btn-warning"
                                                            href="/admin/order/update/${order.id}"
                                                            >Update</a
                                                        >
                                                        <a
                                                            class="btn btn-danger"
                                                            href="/admin/order/delete/${order.id}"
                                                            >Delete</a
                                                        >
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                    <nav aria-label="Page navigation example">
                                        <ul class="pagination justify-content-center">
                                            <li class="page-item">
                                                <a
                                                    class="page-link ${currentPage eq 1 ? 'disabled' : ''}"
                                                    href="/admin/order?page=${currentPage - 1}"
                                                    aria-label="Previous"
                                                >
                                                    <span aria-hidden="true">&laquo;</span>
                                                </a>
                                            </li>
                                            <c:forEach begin="0" end="${totalPage - 1}" varStatus="loop">
                                                <li class="page-item">
                                                    <a
                                                        class="page-link ${(loop.index + 1) eq currentPage ? 'active' : ''}"
                                                        href="/admin/order?page=${loop.index + 1}"
                                                        >${loop.index + 1}</a
                                                    >
                                                </li>
                                            </c:forEach>
                                            <li class="page-item">
                                                <a
                                                    class="page-link ${currentPage eq totalPage ? 'disabled' : ''}"
                                                    href="/admin/order?page=${currentPage + 1}"
                                                    aria-label="Next"
                                                >
                                                    <span aria-hidden="true">&raquo;</span>
                                                </a>
                                            </li>
                                        </ul>
                                    </nav>
                                </div>
                            </div>
                        </div>
                    </div>
                </main>
                <jsp:include page="../layout/footer.jsp" />
            </div>
        </div>
        <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
            crossorigin="anonymous"
        ></script>
        <script src="/js/scripts.js"></script>
    </body>
</html>
