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
                    <div class="col-md-6 text-right">${book.price} &#36;</div>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

<nav aria-label="Page navigation">
    <div class="text-center">
        <ul class="pagination">
            <c:choose>
                <c:when test="${currentPagePosition==1}">
                    <li class="active"><a href="">${currentPagePosition}</a></li>
                </c:when>
                <c:when test="${currentPagePosition==2}">
                    <li><a href="/?page=${currentPagePosition - 1}">${currentPagePosition-1}</a></li>
                    <li class="active"><a href="">${currentPagePosition}</a></li>
                </c:when>
                <c:when test="${currentPagePosition==3}">
                    <li><a href="/?page=${currentPagePosition - 2}">${currentPagePosition-2}</a></li>
                    <li><a href="/?page=${currentPagePosition - 1}">${currentPagePosition-1}</a></li>
                    <li class="active"><a href="">${currentPagePosition}</a></li>
                </c:when>
                <c:otherwise>
                    <li>
                        <a href="/" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                    <li><a href="/?page=${currentPagePosition - 2}">${currentPagePosition-2}</a></li>
                    <li><a href="/?page=${currentPagePosition - 1}">${currentPagePosition-1}</a></li>
                    <li class="active"><a href="">${currentPagePosition}</a></li>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${currentPagePosition==lastPagePosition}">

                </c:when>
                <c:when test="${currentPagePosition==lastPagePosition-1}">
                    <li><a href="/?page=${currentPagePosition + 1}">${currentPagePosition+1}</a></li>
                </c:when>
                <c:when test="${currentPagePosition==lastPagePosition-2}">
                    <li><a href="/?page=${currentPagePosition + 1}">${currentPagePosition+1}</a></li>
                    <li><a href="/?page=${currentPagePosition + 2}">${currentPagePosition+2}</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="/?page=${currentPagePosition + 1}">${currentPagePosition+1}</a></li>
                    <li><a href="/?page=${currentPagePosition + 2}">${currentPagePosition+2}</a></li>
                    <li>
                        <a href="/?page=${lastPagePosition}" aria-label="Next">
                            <span aria-hidden="true">&raquo</span>
                        </a>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</nav>

<script>
    function updateOrder(buttonValue) {
        $('#order_success').hide();
        $('#modal').show();
        $('#orderBookImg').attr('src', buttonValue.dataset.img_url);
        $('#orderBookImg').attr('alt', buttonValue.dataset.title);
        $('#orderBookTitle').html(buttonValue.dataset.title);
        $('#orderBookPrice').html(buttonValue.dataset.price + " &#36;");
        $('input[name="book_title"]').val(buttonValue.dataset.title);
        $('#orderTotal').html('Total : ' + buttonValue.dataset.price + " &#36;");
        $('#orderQuantity').val(1);

        $('#firstName').removeClass('has-error');
        $('#lastName').removeClass('has-error');
        $('#address').removeClass('has-error');
        $('#firstNameHelpBlock').hide();
        $('#lastNameHelpBlock').hide();
        $('#addressHelpBlock').hide();
    }
    function updateTotal() {
        $.ajax({
            url: 'updateTotal',
            data: ({
                quantity: $('#orderQuantity').val(),
                bookTitle: $('input[name="book_title"]').val()
            }),
            success: function (data) {
                $('#orderTotal').html("Total : " + data + " &#36;");
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

                var firstName = $('#firstName');
                var lastName = $('#lastName');
                var address = $('#address');

                var firstNameHelpBlock = $('#firstNameHelpBlock');
                var lastNameHelpBlock = $('#lastNameHelpBlock');
                var addressHelpBlock = $('#addressHelpBlock');

                firstName.removeClass('has-error');
                lastName.removeClass('has-error');
                address.removeClass('address');
                firstNameHelpBlock.hide();
                lastNameHelpBlock.hide();
                addressHelpBlock.hide()
                $.each(result,
                    function (field, errorMessage) {
                        if (field === "success") {
                            $('#modal').hide();
                            $('#order_success').show();
                        } else if (field === 'firstName') {
                            firstName.addClass('has-error');
                            firstNameHelpBlock.html(errorMessage);
                            firstNameHelpBlock.show();
                        } else if (field === 'lastName') {
                            lastName.addClass('has-error');
                            lastNameHelpBlock.html(errorMessage);
                            lastNameHelpBlock.show();
                        } else if (field === 'address') {
                            address.addClass('has-error');
                            addressHelpBlock.html(errorMessage);
                            addressHelpBlock.show();
                        }
                    }
                )
            }
        })
    }
</script>
