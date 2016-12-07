// The root URL for the RESTful services
var loginURI = "http://localhost:8080/login";
var partnerSignupURI = "http://localhost:8080/partnerservice/partners";
var categoriesURI = "http://localhost:8080/categoryservice/categories";

var partnerData;
var authToken = '';
var currentOrder;
var categories;


showPartnerLogin();
selectCategories();

function selectCategories() {
    $.getJSON(categoriesURI, function (data) {
        categories = data.elements;
    });
}

function showPartnerLogin() {
    var content = '<div class="detailItem"><label>Email:</label><input type="text" id="loginEmail" name="email" /></div>';
    content += '<div class="detailItem"><label>Password:</label><input type="password" id="loginPassword" name="password"></div>';
    content += '<div class="detailItem"><button id="btnLogin">Login</button></div>';
    content += '<div class="detailItem"><label>Or Sign up</label></div>';
    content += '<div class="detailItem"><label>Email:</label><input type="text" id="signupEmail" name="email"/></div>';
    content += '<div class="detailItem"><label>Password:</label><input type="password" id="signupPassword" name="password"></div>';
    content += '<div class="detailItem"><label>First Name:</label><input type="text" id="firstName" name="firstName"/></div>';
    content += '<div class="detailItem"><label>Last Name:</label><input type="text" id="lastName" name="lastName"/></div>';
    content += '<div class="detailItem"><label>Company Name:</label><input type="text" id="companyName" name="companyName"/></div>';
    content += '<div class="detailItem"><button id="btnPartnerSignup">Sign Up</button></div>';
    $('#partnerArea').html(content);
}

$('#btnLogin').live('click', function() {

    var email = $('#loginEmail').val();
    if(email == '') {
        window.alert("You must enter email");
        return;
    }
    var password = $('#loginPassword').val();
    if(password == '') {
        window.alert("You must enter password");
        return;
    }

    $.ajax({
        type: 'POST',
        url: loginURI,
        data: 'username=' + email + '&password=' + password,
        crossDomain: true,
        success: function (data) {
            authToken = data.token;
            console.log(data);
            showPartnerPage(data);
        },
        error: function (xhr, desc, err) {
            if(xhr.status == 403){
                showPartnerLogin();
                var jsonResponse = JSON.parse(xhr.responseText);
                window.alert(jsonResponse.message);
            }

            if(xhr.status == 401){
                showPartnerLogin();
                var jsonResponse = JSON.parse(xhr.responseText);
                window.alert(jsonResponse.message);
            }
        }
    });
});

$('#btnPartnerSignup').live('click', function() {

    var email = $('#signupEmail').val();
    if(email == '') {
        window.alert("You must enter email");
        return;
    }
    var password = $('#signupPassword').val();
    if(password == '') {
        window.alert("You must enter password");
        return;
    }

    var firstName = $('#firstName').val();
    if(firstName == '') {
        window.alert("You must enter first name");
        return;
    }

    var lastName = $('#lastName').val();
    if(lastName == '') {
        window.alert("You must enter last name");
        return;
    }

    var companyName = $('#companyName').val();

    var registerData = JSON.stringify({
        "email": email,
        "password": password,
        "firstName": firstName,
        "lastName": lastName,
        "companyName": companyName
    });

    console.log(registerData);
    $.ajax({
        headers: {
            'Content-Type': 'application/json'
        },
        type: 'POST',
        url: partnerSignupURI,
        data: registerData,
        crossDomain: true,
        success: function (data) {
            showPartnerLogin();
            window.alert("Successfully registered. Please login.");
        },
        error: function (xhr, desc, err) {
            if(xhr.status == 403){
                showPartnerLogin();
                var jsonResponse = JSON.parse(xhr.responseText);
                window.alert(jsonResponse.message);
            }
            if(xhr.status == 401){
                showPartnerLogin();
                var jsonResponse = JSON.parse(xhr.responseText);
                window.alert(jsonResponse.message);
            }
        }
    });
});



function showPartnerPage(loginData) {

    $.ajax({
        headers: {
            'X-AUTH-TOKEN': authToken,
            'Content-Type': 'application/json'
        },
        type: loginData.links[0].method,
        url: loginData.links[0].href,
        success: function (partner) {
            partnerData = partner;

            var content = '<div class="detailItem"><label>Company Name</label><span>' + partnerData.companyName + '</span></div>';
            content += '<div class="detailItem"><label>First Name</label><span>' + partnerData.firstName + '</span></div>';
            content += '<div class="detailItem"><label>Last Name</label><span>' + partnerData.lastName + '</span></div>';
            content += '<div class="detailItem"><label>Email</label><span>' + partnerData.email + '</span></div>';
            content += '<div class="detailItem"><label>Phone</label><span>' + partnerData.phone + '</span></div>';
            content += '<div class="detailItem"><label>Address</label><span>' + partnerData.street + ', ' + partnerData.city + ', ' + partnerData.state + ' ' + partnerData.zipCode + '</span></div>';
            content += '<div class="detailItem"><label>Created at</label><span>' + partnerData.createdAt + '</span></div>';
            content += '<div class="detailItem"><button id="showOrders" uri="' + partnerData.links[5].href + '">Orders</button></div>';
            content += '<div class="detailItem"><button id="getProducts" uri="' + partnerData.links[3].href + '">Products</button></div>';
            content += '<div class="detailItem"><button id="addProduct" uri="' + partnerData.links[4].href + '">Add Product</button></div>';
            content += '<div class="detailItem"><button id="editPartner" uri="' + partnerData.links[1].href + '">Edit Partner</button></div>';
            content += '<div class="detailItem"><button id="deletePartner" uri="' + partnerData.links[2].href + '">Delete Partner</button></div>';

            $('#partnerArea').html(content);
        },
        error: function (xhr, desc, err) {
            if(xhr.status == 403){
                showPartnerLogin();
                var jsonResponse = JSON.parse(xhr.responseText);
                window.alert(jsonResponse.message);
            }
            if(xhr.status == 401){
                showPartnerLogin();
                var jsonResponse = JSON.parse(xhr.responseText);
                window.alert(jsonResponse.message);
            }
        }
    });
}

$('#addProduct').live('click', function() {
    showProductAddForm($(this).attr('uri'));
});

$('#showOrders').live('click', function() {

    if(authToken == ''){
        window.alert('Authentication is required! Please Login or Sign up.');
        showPartnerLogin();
        return;
    }

    var status = $('#orderStatus').val();

    $.ajax({
        headers: {
            'X-AUTH-TOKEN': authToken
        },
        type: 'GET',
        url: $(this).attr('uri'),
        data: 'status=' + status,
        crossDomain: true,
        success: function (orders) {

            var list = orders == null ? [] : (orders instanceof Array ? orders : [orders]);

            var content = '';
            $.each(list, function(index, order) {
                content += '<div class="orderList" uri="' + order.links[0].href + '">';
                content += '<div><strong>Order Id: </strong>' + order.id + '</div>';
                content += '<div><strong>Customer : </strong>' + order.customerFirstName + '</div>';
                content += '<div><strong>Created at: </strong>' + order.createdAt + '</div>';
                content += '<div><strong>Status: </strong><span>' + order.lastStatus + '</span></div>';
                content += '<div><strong>Total price: </strong>$' + order.totalPrice.toFixed(2) + '</div>';
                content += '</div>'
            });

            $('#ordersArea').html(content);
        },
        error: function (xhr, desc, err) {
            if(xhr.status == 403){
                showPartnerLogin();
                var jsonResponse = JSON.parse(xhr.responseText);
                window.alert(jsonResponse.message);
            }
        }
    });
});

$('.orderList').live('click', function() {

    if(authToken == ''){
        window.alert('Authentication is required! Please Login or Sign up.');
        showPartnerLogin();
        return;
    }

    $.ajax({
        headers: {
            'X-AUTH-TOKEN': authToken
        },
        type: 'GET',
        url: $(this).attr('uri'),
        crossDomain: true,
        success: function (order) {
            currentOrder = order;
            renderOrder(order);
        },
        error: function (xhr, desc, err) {
            if(xhr.status == 403){
                showPartnerLogin();
                var jsonResponse = JSON.parse(xhr.responseText);
                window.alert(jsonResponse.message);
            }
        }
    });
});

function refreshCurrentOrder() {

    $.ajax({
        headers: {
            'X-AUTH-TOKEN': authToken
        },
        type: currentOrder.links[0].method,
        url: currentOrder.links[0].href,
        crossDomain: true,
        success: function (order) {
            renderOrder(order);
        },
        error: function (xhr, desc, err) {
            if(xhr.status == 403){
                showPartnerLogin();
                var jsonResponse = JSON.parse(xhr.responseText);
                window.alert(jsonResponse.message);
            }
        }
    });

}

function renderOrder(order){

    var list = order.products == null ? [] : (order.products instanceof Array ? order.products : [order.products]);
    var totalPrice = 0;

    var content = '<div class="detailItem"><strong>Order Id: </strong><span>' + order.id + '</span></div>';
    content += '<div class="detailItem"><strong>Customer: </strong><span>' + order.customerFirstName + '</span></div>';

    content += '<ul id="productList">';
    $.each(list, function(index, product) {
        content += '<li class="productInfo">';
        content += '<div class="block"><img src="' + product.picture + '"></div>';
        content += '<div class="block"><div><span class="title">' + product.name + '</span></div><div>$' + product.unitPrice + '</div><div>Quantity: ' + product.quantity + '</div><div style="color: red;">Status: ' + product.status + '</div></div>';
        if(product.status == 'ORDERED'){
            content += '<div class="block"><input type="text" placeholder="tracking number" id="trackingNumber' + product.id + '"/><button identity="'+product.id+'" method="' + product.links[0].method + '" uri="' + product.links[0].href + '" onclick="updateOrderProductStatus(this);">' + product.links[0].rel + '</button></div>';
        } else if (product.status == 'FULFILLED' || product.status == 'SHIPPED'){
            content += '<div class="block"><button identity="'+product.id+'" method="' + product.links[0].method + '" uri="' + product.links[0].href + '" onclick="updateOrderProductStatus(this);">' + product.links[0].rel + '</button></div>';
        }

        content += '</li>';
        totalPrice += product.unitPrice * product.quantity;
    });
    content += '<ul>';

    content += '<div class="detailItem"><strong>Total Price: </strong><span style="color: red">$' + totalPrice.toFixed(2) + '</span></div>';
    content += '<div class="orderItem"><label>Billing address</label><span>' + order.billingAddress + '</div>';
    content += '<div class="orderItem"><label>Shipping address</label><span>' + order.shippingAddress + '</span></div>';

    $('#orderDetailArea').html(content);
}

function updateOrderProductStatus(object, orderId) {

    var uri = $(object).attr('uri');
    var id = $(object).attr('identity');
    var method = $(object).attr('method');

    if(method == 'POST' && $('#trackingNumber' + id).val() == ''){
        window.alert("Please enter your tracking number!");
    }

    var data = '';
    if(method == 'POST') data = 'trackingNumber=' + $('#trackingNumber' + id).val();

    $.ajax({
        headers: {
            'X-AUTH-TOKEN': authToken,
            'Content-Type': 'application/json'
        },
        type: method,
        url: uri,
        data: data,
        crossDomain: true,
        success: function (response) {
            window.alert(response.message);
            $('#showOrders').click();
            refreshCurrentOrder();
        },
        error: function (xhr, desc, err) {
            if(xhr.status == 403){
                showPartnerLogin();
                var jsonResponse = JSON.parse(xhr.responseText);
                window.alert(jsonResponse.message);
            }
        }
    });
}

$('#getProducts').live('click', function() {

    if(authToken == ''){
        window.alert('Authentication is required! Please Login or Sign up.');
        showPartnerLogin();
        return;
    }

    $.ajax({
        headers: {
            'X-AUTH-TOKEN': authToken
        },
        type: 'GET',
        url: $(this).attr('uri'),
        crossDomain: true,
        success: function (products) {
            console.log(products);
            var list = products.elements == null ? [] : (products.elements instanceof Array ? products.elements : [products.elements]);

            var content = '<ul id="productList">';
            $.each(list, function(index, product) {
                content += '<li class="productInfo">';
                content += '<div class="block"><img src="' + product.picture + '"></div>';
                content += '<div class="block"><div><span class="title" uri="' + product.links[0].href + '">' + product.name + '</span></div><div>$' + product.unitPrice + '</div><div>' + product.categoryName + '</div></div>';
                content += '</li>';
            });
            content += '<ul>';
            $('#productListArea').html(content);
        },
        error: function (xhr, desc, err) {
            if(xhr.status == 403){
                showPartnerLogin();
                var jsonResponse = JSON.parse(xhr.responseText);
                window.alert(jsonResponse.message);
            }
        }
    });
});

$('#productList span.title').live('click', function() {

    $.ajax({
        headers: {
            'X-AUTH-TOKEN': authToken
        },
        type: 'GET',
        url: $(this).attr('uri'),
        crossDomain: true,
        success: function (product) {
            var content = '<div class="detailItem"><img id="productPicture" src="' + product.picture + '"></div>';
            content += '<div class="detailItem"><label>Name</label><span id="productName">' + product.name + '</span></div>';
            content += '<div class="detailItem"><label>Price</label><span id="productUnitPrice" rel="' + product.unitPrice + '">$' + product.unitPrice + '</span></div>';
            content += '<div class="detailItem"><label>Weight</label><span>' + product.weight + ' lb</span></div>';
            content += '<div class="detailItem"><label>Category</label><span>' + product.categoryName + '</span></div>';
            content += '<div class="detailItem"><label>Brand</label><span>' + product.brandName + '</span></div>';
            content += '<div class="detailItem"><label>Description</label><span>' + product.description + '</span></div>';
            content += '<div class="detailItem"><button id="updateProduct" uri="' + product.links[1].href + '">Update</button></div>';
            content += '<div class="detailItem"><button id="deleteProduct" uri="' + product.links[2].href + '">Delete</button></div>';
            $('#productDetailArea').html(content);
        },
        error: function (xhr, desc, err) {
            if(xhr.status == 403){
                showPartnerLogin();
                var jsonResponse = JSON.parse(xhr.responseText);
                window.alert(jsonResponse.message);
            }
        }
    });
});

function showProductAddForm(url) {

    var content = '<div class="detailItem"><label>Name: </label><input type="text" id="productName" /></div>';
    content += '<div class="detailItem"><label>Picture: </label><input type="text" id="productPicture" /></div>';
    content += '<div class="detailItem"><label>Brand: </label><input type="text" id="productBrandName" /></div>';
    content += '<div class="detailItem"><label>Description: </label><input type="text" id="productDescription" /></div>';
    content += '<div class="detailItem"><label>Status: </label><select id="productStatus"><option value="true">Enabled</option><option value="false">Disabled</option></select></div>';
    content += '<div class="detailItem"><label>Quantity in stock: </label><input type="text" id="productQuantityInStock" /></div>';
    content += '<div class="detailItem"><label>Unit price: </label><input type="text" id="productUnitPrice" /></div>';
    content += '<div class="detailItem"><label>Weight: </label><input type="text" id="productWeight" /></div>';
    content += '<div class="detailItem"><label>categoryId:</label><select id="productCategoryId">';
    $.each(categories, function(index, category) {
        content += '<option value="' + category.id + '">' + category.name + '</option>';
    });
    content += '</select></div>';
    content += '<div class="detailItem"><button id="insertProduct" uri="' + url + '">Save</button></div>';
    $('#productAddArea').html(content);
}

$('#deleteProduct').live('click', function() {

    if(authToken == ''){
        window.alert('Authentication is required! Please Login or Sign up.');
        showPartnerLogin();
        return;
    }

    $.ajax({
        headers: {
            'X-AUTH-TOKEN': authToken,
        },
        type: 'DELETE',
        url: $(this).attr('uri'),
        crossDomain: true,
        success: function (data) {
            $('#getProducts').click();
            $('#productDetailArea').html('');
        },
        error: function (xhr, desc, err) {
            if(xhr.status == 403){
                showCustomerLogin();
                var jsonResponse = JSON.parse(xhr.responseText);
                window.alert(jsonResponse.message);
            }
        }
    });

});

$('#insertProduct').live('click', function() {

    var productData = JSON.stringify({
        "name": $('#productName').val(),
        "picture": $('#productPicture').val(),
        "brandName": $('#productBrandName').val(),
        "description": $('#productDescription').val(),
        "status": $('#productStatus').val(),
        "quantityInStock": $('#productQuantityInStock').val(),
        "unitPrice": $('#productUnitPrice').val(),
        "weight": $('#productWeight').val(),
        "categoryId": $('#productCategoryId').val()
    });

    if(authToken == ''){
        window.alert('Authentication is required! Please Login or Sign up.');
        showPartnerLogin();
        return;
    }

    $.ajax({
        headers: {
            'X-AUTH-TOKEN': authToken,
            'Content-Type': 'application/json'
        },
        type: 'POST',
        url: $(this).attr('uri'),
        data: productData,
        crossDomain: true,
        success: function (data) {
            $('#addProduct').click();
            $('#getProducts').click();
        },
        error: function (xhr, desc, err) {
            if(xhr.status == 403){
                showCustomerLogin();
                var jsonResponse = JSON.parse(xhr.responseText);
                window.alert(jsonResponse.message);
            }
        }
    });
});
