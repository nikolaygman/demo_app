<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<div class="container">
    <tiles:insertAttribute name="order_form"/>
    <c:forEach items="${allBooks}" var="book">
        <div class="col-md-6">
            <div class="row">
                <div class="col-md-12">
                    <h2>
                        <c:out value="${book.title}"/>
                    </h2>
                </div>
            </div>
            <div class="row" id="book-description">
                <div class="col-md-4">
                    <img class="img-thumbnail" src='<c:out value="${book.img_url }" />'
                         alt="<c:out value="${book.title}" />" height="386px" width="250px">
                </div>

                <div class="col-md-8">
                    <c:out value="${book.description }"/>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <c:forEach items="${book.genres}" var="genre">
						<span class="label label-default"><c:out
                                value="${genre.name}"/></span>
                    </c:forEach>
                </div>
                <div class="col-md-12" id="book-authors">
                    Authors:
                    <c:forEach items="${book.authors}" var="author">
                        <c:out value="${author.name}"/>
                    </c:forEach>
                </div>
                <div class="row">
                    <div class="col-md-5" id="book-order-btn">

                        <!-- Trigger the modal with a button -->
                        <button type="button" class="btn btn-default" data-toggle="modal"
                                data-target="#myModal" data-img_url="${book.img_url}"
                                data-title="${book.title}" data-price="${book.price}"
                                onclick="updateOrder(this)">Buy
                        </button>

                    </div>
                    <div class="col-md-6 text-right">${book.price}</div>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
<script>
    function updateOrder(buttonValue) {
        $('#order_success').hide();
        $('#modal').show();
        $('#orderBookImg').attr('src', buttonValue.dataset.img_url);
        $('#orderBookImg').attr('alt', buttonValue.dataset.title);
        $('#orderBookTitle').val(buttonValue.dataset.title);
        $('input[name="book_title"]').val(buttonValue.dataset.title);
        $('#orderTotal').html('Total : ' + buttonValue.dataset.price);
        $('#orderQuantity').val(1);
        $('#error_container').hide();
    }
    function updateTotal() {
        $.ajax({
            url: 'updateTotal',
            data: ({
                quantity: $('#orderQuantity').val(),
                bookTitle: $('#orderBookTitle').html()
            }),
            success: function (data) {
                $('#orderTotal').html("Total : " + data);
            }
        });
    }
    function submitOrder() {
        var form = $('#order_form').serialize();
        $.ajax({
            type: 'post',
            url: 'make_order',
            data: form,
            success: function (result) {
                if (result === "no_errors") {
                    $('#modal').hide();
                    $('#order_success').show();
                }
                else {
                    $('#error_container').html(result);
                    $('#error_container').show();
                }
            }
        });
    }
</script>
