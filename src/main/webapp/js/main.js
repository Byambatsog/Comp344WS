// The root URL for the RESTful services
var productSearchURI = "http://localhost:8080/productservice/products";
var loginURI = "http://localhost:8080/login";
var customerSignupURI = "http://localhost:8080/customerservice/customers";

var customerData;
var shoppingCart = [];
var authToken = '';

//Retrieve all products
searchProducts('');
showCustomerLogin();

function searchProducts(searchKey) {
	var queryParam = '';
	if(searchKey != ''){
		queryParam = '?q=' + searchKey;
	}
	$.getJSON(productSearchURI + queryParam, function (products) {
		console.log(products)
		renderProductList(products);
	});
}

function renderProductList(products) {
	// JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
	var list = products.elements == null ? [] : (products.elements instanceof Array ? products.elements : [products.elements]);

	var content = '<ul id="productList">';
	$.each(list, function(index, product) {
		content += '<li class="productInfo">';
		content += '<div class="block"><img src="' + product.picture + '"></div>';
		content += '<div class="block"><div><span class="title" uri="' + product.links[0].href + '">' + product.name + '</span></div><div>$' + product.unitPrice + '</div><div>' + product.partnerName + '</div></div>';
		content += '</li>';
	});

	content += '<ul>';

	$.each(products.links, function(index, link) {
		content += '<button id="pagingProduct"  onclick="listProduct(\'' + link.href + '\')">'+ link.rel +'</button>'
	});

	$('#listArea').html(content);
}

function listProduct(url) {
	$.getJSON(url, function (products) {
		renderProductList(products);
	});
}

// Register listeners
$('#btnSearch').click(function() {
	searchProducts($('#searchKey').val());
	return false;
});

// Trigger search when pressing 'Return' on search key input field
$('#searchKey').keypress(function(e){
	if(e.which == 13) {
		searchProducts($('#searchKey').val());
		e.preventDefault();
		return false;
    }
});

$('#productList span.title').live('click', function() {
	$.getJSON($(this).attr('uri'), function (product) {
		var content = '<div class="detailItem"><img id="productPicture" src="' + product.picture + '"></div>';
		content += '<div class="detailItem"><label>Name</label><span id="productName">' + product.name + '</span></div>';
		content += '<div class="detailItem"><label>Price</label><span id="productUnitPrice" rel="' + product.unitPrice + '">$' + product.unitPrice + '</span></div>';
		content += '<div class="detailItem"><label>Weight</label><span>' + product.weight + ' lb</span></div>';
		content += '<div class="detailItem"><label>Category</label><span>' + product.categoryName + '</span></div>';
		content += '<div class="detailItem"><label>Brand</label><span>' + product.brandName + '</span></div>';
		content += '<div class="detailItem"><label>Seller</label><span id="productPartner" style="cursor: pointer" uri="' + product.links[1].href + '">' + product.partnerName + '</span></div>';
		content += '<div class="detailItem"><label>Description</label><span>' + product.description + '</span></div>';
		content += '<div class="detailItem"><button id="addToCart" identity="' + product.id + '" uri="' + product.links[4].href + '">Add to Cart</button></div>';
		content += '<div class="detailItem"><button id="reviews" uri="' + product.links[2].href + '">Reviews</button></div>';
		content += '<div class="detailItem">';
		content += '<label>Title</label>';
		content += '<span><input type="text" id="reviewTitle"></span>';
		content += '<label>Rating</label>';
		content += '<span><select id="reviewRating"><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option></select></span>';
		content += '<label>Comment</label>';
		content += '<span><textarea id="reviewText"></textarea></span>';
		content += '<button id="postReview" uri="' + product.links[3].href + '">Post Review</button>';
		content += '</div>';
		$('#detailArea').html(content);
	});
});

$('#reviews').live('click', function() {
	$.getJSON($(this).attr('uri'), function (reviews) {
		renderReviewList(reviews);
	});
});

function renderReviewList(reviews) {
	var list = reviews == null ? [] : (reviews instanceof Array ? reviews : [reviews]);
	var content = '';
	$.each(list, function(index, review) {
		content += '<div class="review"><label>"' + review.title + '" (rating: "' + review.rating + '")</label><span>"' + review.comment + '"</span><div>"' + review.customerName + '"</div></div>';
	});
	$('#supportArea').html(content);
}

$('#addToCart').live('click', function() {
	var itemId = $(this).attr('identity');
	var itemName = $('#productName').html();
	var itemPicture = $('#productPicture').attr('src');
	var itemUnitPrice = $('#productUnitPrice').attr('rel');

	var list = shoppingCart == null ? [] : (shoppingCart instanceof Array ? shoppingCart : [shoppingCart]);
	var found = false;
	$.each(list, function(index, item) {
		if(item.productId == itemId){
			item.quantity += 1;
			found = true;
		}
	});
	if(!found){
		shoppingCart[shoppingCart.length] = {productId : itemId, name: itemName, unitPrice:itemUnitPrice, picture:itemPicture, quantity : 1 };
	}
	showShoppingCart();
});

function showShoppingCart() {

	var list = shoppingCart == null ? [] : (shoppingCart instanceof Array ? shoppingCart : [shoppingCart]);
	var totalPrice = 0;

	var content = '<ul id="productList">';
	$.each(list, function(index, product) {
		content += '<li class="productInfo">';
		content += '<div class="block"><img src="' + product.picture + '"></div>';
		content += '<div class="block"><div><span class="title">' + product.name + '</span></div><div>Unit Price: $' + product.unitPrice + '</div><div>Quantity: ' + product.quantity + '</div></div>';
		content += '</li>';
		totalPrice += product.unitPrice * product.quantity;
	});
	content += '<ul>';
	content += '<div class="detailItem"><strong>Total Price: </strong><span style="color: red">$' + totalPrice.toFixed(2) + '</span></div>';
	content += '<div class="detailItem"><button id="Checkout" onclick="checkOut();">Check Out</button></div>';

	$('#shoppingCartArea').html(content);
}

$('#postReview').live('click', function() {

	var reviewData = JSON.stringify({
		"title": $('#reviewTitle').val(),
		"comment": $('#reviewText').val(),
		"rating": $('#reviewRating').val()
	});

	if(authToken == ''){
		window.alert('Authentication is required! Please Login or Sign up.');
		showCustomerLogin();
		return;
	}

	$.ajax({
		headers: {
			'X-AUTH-TOKEN': authToken,
			'Content-Type': 'application/json'
		},
		type: 'POST',
		url: $(this).attr('uri'),
		data: reviewData,
		crossDomain: true,
		success: function (data) {
			$('#reviews').click();
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

$('#productPartner').live('click', function() {
	$.getJSON($(this).attr('uri'), function (partner) {
		var content = '<div class="detailItem"><label>Company Name</label><span>' + partner.companyName + '</span></div>';
		content += '<div class="detailItem"><label>Executive</label><span>' + partner.firstName + ' ' + partner.lastName + '</span></div>';
		content += '<div class="detailItem"><label>Type</label><span>' + partner.type + '</span></div>';
		content += '<div class="detailItem"><label>Phone</label><span>' + partner.phone + '</span></div>';
		content += '<div class="detailItem"><label>Email</label><span>' + partner.email + '</span></div>';
		content += '<div class="detailItem"><button id="partnerProducts" uri=' + partner.links[0].href + '>Products</button></div>';
		$('#supportArea').html(content);
	});
});

$('#partnerProducts').live('click', function() {
	$.getJSON($(this).attr('uri'), function (products) {
		renderProductList(products);
	});
});

function showCustomerLogin() {
	var content = '<div class="detailItem"><label>Email:</label><input type="text" id="loginEmail" name="email" /></div>';
	content += '<div class="detailItem"><label>Password:</label><input type="password" id="loginPassword" name="password"></div>';
	content += '<div class="detailItem"><button id="btnLogin">Login</button></div>';
	content += '<div class="detailItem"><label>Or Sign up</label></div>';
	content += '<div class="detailItem"><label>Email:</label><input type="text" id="signupEmail" name="email"/></div>';
	content += '<div class="detailItem"><label>Password:</label><input type="password" id="signupPassword" name="password"></div>';
	content += '<div class="detailItem"><label>FirstName:</label><input type="text" id="firstName" name="firstName"/></div>';
	content += '<div class="detailItem"><label>LastName:</label><input type="text" id="lastName" name="lastName"/></div>';
	content += '<div class="detailItem"><button id="btnCustomerSignup">Sign Up</button></div>';
	$('#customerArea').html(content);
}

$('#btnCustomerSignup').live('click', function() {

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

	var registerData = JSON.stringify({
		"email": email,
		"password": password,
		"firstName": firstName,
		"lastName": lastName
	});

	console.log(registerData);
	$.ajax({
		headers: {
			'Content-Type': 'application/json'
		},
		type: 'POST',
		url: customerSignupURI,
		data: registerData,
		crossDomain: true,
		success: function (data) {
			showCustomerLogin();
			window.alert("Successfully registered. Please login.");
		},
		error: function (xhr, desc, err) {
			if(xhr.status == 403){
				showCustomerLogin();
				var jsonResponse = JSON.parse(xhr.responseText);
				window.alert(jsonResponse.message);
			}
			if(xhr.status == 401){
				showCustomerLogin();
				var jsonResponse = JSON.parse(xhr.responseText);
				window.alert(jsonResponse.message);
			}
		}
	});
});

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
			showCustomerPage(data);
		},
		error: function (xhr, desc, err) {
			if(xhr.status == 403){
				showCustomerLogin();
				var jsonResponse = JSON.parse(xhr.responseText);
				window.alert(jsonResponse.message);
			}

			if(xhr.status == 401){
				showCustomerLogin();
				var jsonResponse = JSON.parse(xhr.responseText);
				window.alert(jsonResponse.message);
			}
		}
	});
});

function showCustomerPage(loginData) {

	$.ajax({
		headers: {
			'X-AUTH-TOKEN': authToken,
			'Content-Type': 'application/json'
		},
		type: loginData.links[0].method,
		url: loginData.links[0].href,
		success: function (customer) {
			customerData = customer;

			var content = '<div class="detailItem"><label>First Name</label><span>' + customerData.firstName + '</span></div>';
			content += '<div class="detailItem"><label>Last Name</label><span>' + customerData.lastName + '</span></div>';
			content += '<div class="detailItem"><label>Email</label><span>' + customerData.email + '</span></div>';
			content += '<div class="detailItem"><label>Created at</label><span>' + customerData.createdAt + '</span></div>';
			content += '<div class="detailItem"><button id="editUser" uri="' + customerData.links[1].href + '">Edit User</button></div>';
			content += '<div class="detailItem"><button id="deleteUser" uri="' + customerData.links[2].href + '">Delete User</button></div>';
			content += '<div class="detailItem"><button id="getAddresses" uri="' + customerData.links[3].href + '">Addresses</button></div>';
			content += '<div class="detailItem"><button id="addAddress" uri="' + customerData.links[4].href + '">Add Address</button></div>';
			content += '<div class="detailItem"><button id="getCreditCards" uri="' + customerData.links[5].href + '">Credit cards</button></div>';
			content += '<div class="detailItem"><button id="addCreditCard" uri="' + customerData.links[6].href + '">Add credit card</button></div>';
			content += '<div class="detailItem"><button id="showOrders" uri="' + customerData.links[7].href + '">Orders</button></div>';
			$('#customerArea').html(content);
		},
		error: function (xhr, desc, err) {
			if(xhr.status == 403){
				showCustomerLogin();
				var jsonResponse = JSON.parse(xhr.responseText);
				window.alert(jsonResponse.message);
			}
			if(xhr.status == 401){
				showCustomerLogin();
				var jsonResponse = JSON.parse(xhr.responseText);
				window.alert(jsonResponse.message);
			}
		}
	});
}

function checkOut() {

	if(customerData == null || customerData == undefined || authToken == ''){
		window.alert('Authentication is required! Please Login or Sign up.');
		showCustomerLogin();
		return;
	}

	var list = shoppingCart == null ? [] : (shoppingCart instanceof Array ? shoppingCart : [shoppingCart]);
	var totalPrice = 0;

	var content = '<div class="detailItem"><table cellpadding="5" cellspacing="0">';
	content += '<tr><td>#</td><td>Name</td><td>Price</td><td>Q</td></tr>';

	$.each(list, function(index, product) {
		content += '<tr>';
		content += '<td>' + (index + 1) + '.</td><td>' + product.name + '</td><td>$' + product.unitPrice + '</td><td>' + product.quantity + '</td>';
		content += '</tr>';
		totalPrice += product.unitPrice * product.quantity;
	});
	content += '</table></div>';
	content += '<div class="detailItem"><strong>Total Price: </strong><span style="color: red">$' + totalPrice.toFixed(2) + '</span></div>';

	content += '<div class="orderItem"><label>Billing address</label><span><select id="billingAddressId"></select></span></div>';
	content += '<div class="orderItem"><label>Shipping address</label><span><select id="shippingAddressId"></select></span></div>';
	content += '<div class="orderItem"><label>Credit cards</label><span><select id="creditCardId"></select></span></div>';

	$.ajax({
		headers: {
			'X-AUTH-TOKEN': authToken
		},
		type: customerData.links[3].method,
		url: customerData.links[3].href,
		success: function (addresses) {
			var billingAddresses = '';
			var shippingAddresses = '';
			var listAddress = addresses == null ? [] : (addresses instanceof Array ? addresses : [addresses]);
			$.each(listAddress, function(index, address) {
				billingAddresses += '<option value="' + address.id + '">' + address.street + '</option>';
				shippingAddresses += '<option value="' + address.id + '">' + address.street + '</option>';
			});

			$('#billingAddressId').html(billingAddresses);
			$('#shippingAddressId').html(shippingAddresses);
		}
	});

	$.ajax({
		headers: {
			'X-AUTH-TOKEN': authToken
		},
		type: customerData.links[5].method,
		url: customerData.links[5].href,
		success: function (cards) {
			var creditCards = '';
			var listCard = cards == null ? [] : (cards instanceof Array ? cards : [cards]);
			$.each(listCard, function(index, card) {
				creditCards += '<option value="' + card.id + '">' + card.cardNumber + ' - ' + card.cardType + '</option>';
			});

			$('#creditCardId').html(creditCards);
		}
	});

	content += '<div class="orderItem"><button id="placeAnOrder" onclick="placeAnOrder();">Place an order</button></div>';
	$('#orderArea').html(content);
}

function placeAnOrder() {

	if(authToken == ''){
		window.alert('Authentication is required! Please Login or Sign up.');
		showCustomerLogin();
		return;
	}

	var cartItems = [shoppingCart.length];

	$.each(shoppingCart, function(index, item) {
		cartItems[index] = {productId: item.productId, quantity: item.quantity}
	});

	var orderData = JSON.stringify({
		"shippingAddressId": $('#shippingAddressId').val(),
		"billingAddressId": $('#billingAddressId').val(),
		"creditCardId": $('#creditCardId').val(),
		"cartItems": cartItems
	});

	$.ajax({
		headers: {
			'X-AUTH-TOKEN': authToken,
			'Content-Type': 'application/json'
		},
		type: customerData.links[8].method,
		url: customerData.links[8].href,
		data: orderData,
		crossDomain: true,
		success: function (order) {
			console.log(order);
			renderOrder(order);
		},
		error: function (xhr, desc, err) {
			if(xhr.status == 403){
				showCustomerLogin();
				var jsonResponse = JSON.parse(xhr.responseText);
				window.alert(jsonResponse.message);
			}
			if(xhr.status == 401){
				showCustomerLogin();
				var jsonResponse = JSON.parse(xhr.responseText);
				window.alert(jsonResponse.message);
			}
		}
	});
}

function renderOrder(order){
	var list = order.products == null ? [] : (order.products instanceof Array ? order.products : [order.products]);
	var totalPrice = 0;

	var content = '<div class="detailItem"><table cellpadding="5" cellspacing="0">';
	content += '<thead><td>#</td><td>Name</td><td>Price</td><td>Q</td><td title="status">S</td></thead>';

	$.each(list, function(index, product) {

		var status = '';
		if(product.status == 'ORDERED'){
			status = 'O'
		} else if(product.status == 'FULFILLED'){
			status = 'F'
		} else if(product.status == 'SHIPPED'){
			status = 'S'
		} else if(product.status == 'DELIVERED'){
			status = 'D'
		} else if(product.status == 'CANCELLED'){
			status = 'C'
		}



		content += '<tr>';
		content += '<td>' + (index + 1) + '.</td><td>' + product.name + '</td><td>$' + product.unitPrice + '</td><td>' + product.quantity + '</td><td title="' + product.status + '">' + status + '</td>';
		content += '</tr>';
		totalPrice += product.unitPrice * product.quantity;
	});
	content += '</table></div>';
	content += '<div class="detailItem"><strong>Total Price: </strong><span style="color: red">$' + totalPrice.toFixed(2) + '</span></div>';

	content += '<div class="orderItem"><label>Billing address</label><span>' + order.billingAddress + '</div>';
	content += '<div class="orderItem"><label>Shipping address</label><span>' + order.shippingAddress + '</span></div>';
	content += '<div class="orderItem"><label>Credit cards</label><span>' + order.paymentCardNumber + ' - ' + order.paymentCardType + '</span></div>';
	content += '<div class="orderItem"><label>Status</label><span style="color: red;">' + order.lastStatus + '</span></div>';

	content += '<div class="orderItem"><button id="cancelOrder" uri="' + order.links[1].href + '">Cancel an order</button></div>';
	$('#orderDetailArea').html(content);

	var orderStatus = '<table cellpadding="5" cellspacing="0"><thead><td>#</td><td>Status</td><td>Date</td></thead>';

	$.each(order.statuses, function(index, status) {
		orderStatus += '<tr>';
		orderStatus += '<td>' + (index + 1) + '.</td><td>' + status.status + '</td><td>' + status.created + '</td>';
		orderStatus += '</tr>';
	});

	orderStatus += '</table>';
	$('#orderStatusArea').html(orderStatus);
}


$('#showOrders').live('click', function() {

	if(authToken == ''){
		window.alert('Authentication is required! Please Login or Sign up.');
		showCustomerLogin();
		return;
	}

	$.ajax({
		headers: {
			'X-AUTH-TOKEN': authToken
		},
		type: 'GET',
		url: $(this).attr('uri'),
		crossDomain: true,
		success: function (orders) {

			var list = orders == null ? [] : (orders instanceof Array ? orders : [orders]);

			var content = '';
			$.each(list, function(index, order) {
				content += '<div class="orderList" uri="' + order.links[0].href + '">';
				content += '<div><strong>Order Id: </strong>' + order.id + '</div>';
				content += '<div><strong>Created at: </strong>' + order.createdAt + '</div>';
				content += '<div><strong>Status: </strong><span>' + order.lastStatus + '</span></div>';
				content += '<div><strong>Total price: </strong>$' + order.totalPrice + '</div>';
				content += '</div>'
			});

			$('#orderArea').html(content);
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

function refreshOrder(url) {

	if (authToken == '') {
		window.alert('Authentication is required! Please Login or Sign up.');
		showCustomerLogin();
		return;
	}

	$.ajax({
		headers: {
			'X-AUTH-TOKEN': authToken
		},
		type: 'GET',
		url: url,
		crossDomain: true,
		success: function (order) {
			renderOrder(order);

		},
		error: function (xhr, desc, err) {
			if (xhr.status == 403) {
				showCustomerLogin();
				var jsonResponse = JSON.parse(xhr.responseText);
				window.alert(jsonResponse.message);
			}
		}
	});
}

$('.orderList').live('click', function() {

	if(authToken == ''){
		window.alert('Authentication is required! Please Login or Sign up.');
		showCustomerLogin();
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
			renderOrder(order);

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

$('#cancelOrder').live('click', function() {

	if(authToken == ''){
		window.alert('Authentication is required! Please Login or Sign up.');
		showCustomerLogin();
		return;
	}

	$.ajax({
		headers: {
			'X-AUTH-TOKEN': authToken
		},
		type: 'DELETE',
		url: $(this).attr('uri'),
		crossDomain: true,
		success: function (response) {
			window.alert(response.message);
			$('#showOrders').click();
			$('#orderStatusArea').html('');
			$('#orderDetailArea').html('');
		},
		error: function (xhr, desc, err) {
			if(xhr.status == 403){
				showCustomerLogin();
				var jsonResponse = JSON.parse(xhr.responseText);
				window.alert(jsonResponse.message);
			}
			if(xhr.status == 401){
				showCustomerLogin();
				var jsonResponse = JSON.parse(xhr.responseText);
				window.alert(jsonResponse.message);
			}
		}
	});
});