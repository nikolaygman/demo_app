<!-- Modal -->
<div class="modal fade " id="myModal" role="dialog">
    <div class="modal-dialog ">

        <!-- Modal content-->
        <div class="modal-content">

            <!-- order_success -->
            <div id="order_success" style="display: none">
                <div class="modal-body alert alert-success" style="margin: 0"><h3>Order submitted</h3></div>
                <div class="modal-footer">
                    <a class="close" href="/orders" style="margin-left: 30px">Checkout orders</a>
                    <button type="button" class="close" data-dismiss="modal">Continue</button>
                </div>
            </div>
            <!-- End of order_success -->
            <div id="modal">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Order submitting</h4>
                </div>
                <div class="modal-body">


                    <form id="order_form" class="form-horizontal small">
                        <div class="form-group ">
                            <label class="col-sm-2 control-label"> Book </label>
                            <div class="col-sm-3">
                                <img class="img-thumbnail" id="orderBookImg" alt="" src=""
                                     height="150px" width="100px">
                            </div>
                            <div id="orderBookTitle" class="col-sm-5"
                                 style="padding-top: 7px"></div>

                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"> Price </label>
                            <div id="orderBookPrice" class="col-sm-6"
                                 style="padding-top: 7px"></div>
                        </div>
                        <div class="form-group">

                            <label class="col-sm-2 control-label">Quantity </label>
                            <div class="col-sm-6">
                                <input id="orderQuantity" type="number" min="1"
                                       class="form-control" form="order_form" value="1"
                                       onchange="updateTotal()" name="quantity"
                                       style="width: 75px">
                            </div>

                        </div>
                        <div id="firstName" class="form-group ">
                            <label class="col-sm-2 control-label">First name</label>
                            <div class="col-sm-7">
                                <input name="firstName" type="text"
                                       class="form-control" value="${order.firstName }">
                                <span id="firstNameHelpBlock" class="help-block"></span>
                            </div>

                        </div>
                        <div id="lastName" class="form-group ">
                            <label class="col-sm-2 control-label">Last name</label>
                            <div class="col-sm-7">
                                <input name="lastName" type="text"
                                       class="form-control" value="${order.lastName }">
                                <span id="lastNameHelpBlock" class="help-block"></span>
                            </div>
                        </div>
                        <div id="address" class="form-group">
                            <label class="col-sm-2 control-label">Address</label>
                            <div class="col-sm-10">
                                <input name="address" type="text"
                                       class="form-control" value="${order.address }">
                                <span id="addressHelpBlock" class="help-block"></span>
                            </div>
                        </div>
                        <input name="${_csrf.parameterName }" value="${_csrf.token }"
                               type="hidden">
                        <input name="book_title" value=""
                               type="hidden">
                    </form>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-default" type="button" form="order_form"
                            onclick="submitOrder()">Submit order
                    </button>
                    <h4 style="float: left; padding-top: 7px; margin: 0"
                        id="orderTotal">Total :</h4>
                </div>
            </div>
        </div>
    </div>
</div>