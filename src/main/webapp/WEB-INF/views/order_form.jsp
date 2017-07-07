<!-- Modal -->
<div class="modal fade " id="myModal" role="dialog">
    <div class="modal-dialog modal-lg">

        <!-- Modal content-->
        <div class="modal-content">

            <!-- order_success -->
            <div id="order_success" style="display: none">
                <div class="modal-body alert alert-success" style="margin: 0"><h3>Order submitted</h3></div>
                <div class="modal-footer">
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
                    <table class="table " style="margin-bottom: 0">
                        <tbody>
                        <tr>
                            <td colspan="2">
                                <div id="error_container" class="small alert alert-danger"
                                     role="alert"></div>
                            </td>
                        </tr>
                        <tr>
                            <td style="border-right: 1px solid #DDDDDD" class="small">
                                <form id="order_form" class="form-horizontal small">
                                    <div class="form-group ">
                                        <label class="col-sm-4 control-label">First name</label>
                                        <div class="col-sm-6">
                                            <input id="firstName" name="firstName" type="text"
                                                   class="form-control" value="${order.firstName }">
                                        </div>
                                    </div>
                                    <div class="form-group text-left">
                                        <label class="col-sm-4 control-label">Last name</label>
                                        <div class="col-sm-6">
                                            <input id="lastName" name="lastName" type="text"
                                                   class="form-control" value="${order.lastName }">
                                        </div>
                                    </div>
                                    <div class="form-group text-left">
                                        <label class="col-sm-4 control-label">Address</label>
                                        <div class="col-sm-6">
                                            <input id="address" name="address" type="text"
                                                   class="form-control" value="${order.address }">
                                        </div>
                                    </div>
                                    <input name="${_csrf.parameterName }" value="${_csrf.token }"
                                           type="hidden">
                                    <input id="#orderBookTitle" name="book_title" value=""
                                           type="hidden">
                                </form>
                            </td>

                            <td class="small">
                                <form class="form-horizontal small">
                                    <div class="form-group ">
                                        <label class="col-sm-4 control-label"> Book </label>
                                        <div class="col-sm-3">
                                            <img id="orderBookImg" alt="" src=""
                                                 height="150px" width="100px">
                                        </div>
                                        <div id="orderBookTitle" class="col-sm-5"
                                             style="padding-top: 7px"></div>

                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-4 control-label"> Price </label>
                                        <div id="orderBookPrice" class="col-sm-6"
                                             style="padding-top: 7px"></div>
                                    </div>
                                    <div class="form-group">

                                        <label class="col-sm-4 control-label">Quantity </label>
                                        <div class="col-sm-6">
                                            <input id="orderQuantity" type="number" min="1"
                                                   class="form-control" form="order_form" value="1"
                                                   onchange="updateTotal()" name="quantity"
                                                   style="width: 75px">
                                        </div>

                                    </div>
                                </form>
                            </td>
                        </tr>
                        </tbody>
                    </table>
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