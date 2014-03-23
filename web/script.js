/**
 * @author Meldanor
 */
$(document).ready(function() {

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
		var lectureTitle = $("input:first").val();
		var formGroup = $('#createFormGroup');
		$.ajax({
			type : "post",
			url : "../api/lecture/create",
			data : lectureTitle,
			contentType : "application/json",
			success : function(data, textData, jqxhr) {
				formGroup.removeClass("has-error").addClass("has-success").addClass("has-feedback");
				$('#createFormGroupIcon').toggle();
			},
			error : function(jqxhr, textData, errorThrown) {
				formGroup.removeClass("has-success").addClass("has-error").addClass("has-feedback");
				$('#createFormGroupIcon').toggle();
			}
		});
		event.preventDefault();
	});

	$('#lectureTitleInput').focusout(function() {
		var lectureTitle = $("input:first").val();
		var formGroup = $('#createFormGroup');
		$.ajax({
			type : "post",
			url : "../api/lecture/exists",
			data : lectureTitle,
			contentType : "application/json",
			success : function(data, textData, jqxhr) {
				formGroup.removeClass("has-success").addClass("has-error").toggleClass("has-feedback");
			},
			statusCode: {
				204: function(xhr) {
					formGroup.removeClass("has-success").removeClass("has-error").removeClass("has-feedback");
				}
			}
		});
	});
});

