/**
 * @author Meldanor
 */
$(document).ready(function() {

	// $.ajax({
	// type: "post",
	// url: "../api/lecture/create",
	// data: "testLecture",
	// contentType: "application/json",
	// success: function(data, textData, jqxhr) {
	// alert(textData);
	// },
	// error: function(jqxhr, textData, errorThrown) {
	// alert(textData);
	// }
	//
	// });

	//$.getJSON("http://localhost:8123/lecture/create/Test",function(data) {
	//alert(data);
	//});
	// $.post("http://localhost:8123/lecture/create", "'Test'", function(data) {
	// alert("Data Loaded: " + data);
	// }, "json").fail(function () {
	// alert(data);
	// });

	$('#mainTab a[href="#show"]').click(function(e) {
		e.preventDefault();
		$(this).tab('show');

		//		show("Hi");
	});
	$('#mainTab a[href="#edit"]').click(function(e) {
		e.preventDefault();
		$(this).tab('show');
		//		show("Hell");
	});
	$('#mainTab a[href="#add"]').click(function(e) {
		e.preventDefault();
		$(this).tab('show');
		//		show("Moin");
	});

	$('#collapseOne').on('show.bs.collapse', function() {
		$(this).html('<div class="panel-body">1. Dynamisch erzeugte Website</div>');
	});
	$('#collapseTwo').on('show.bs.collapse', function() {
		$(this).html('<div class="panel-body">2. Dynamisch erzeugte Website</div>');
	});
	$('#collapseThree').on('show.bs.collapse', function() {
		$(this).html('<div class="panel-body">3. Dynamisch erzeugte Website</div>');
	});

	$('#createForm').submit(function(event) {
		var input = $("input:first").val();
		var formGroup = $('#createFormGroup');
		formGroup.toggleClass("has-success").toggleClass("has-feedback");
		var bla = $('#createFormGroupIcon').toggle();
		event.preventDefault();
	});
});

var show = function(text) {
	alert(text);
};
