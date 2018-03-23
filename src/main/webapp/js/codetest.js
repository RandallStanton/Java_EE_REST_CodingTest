/**
 * Loads the list of employees from the rest service into the table
 * 
 * AJAX
 */
function loadTableData() {
	$("#employee-table").tabulator("setData");
	// $.ajax({
	// url:
	// }).then(function(data) {
	// $("#employee-table").tabulator("setData",data);
	// });
}

/**
 * Clears the employee form data and rests the button states
 */
function clearFormForNew() {
	$("#employee").trigger("reset");
	$("#employee-table").tabulator("deselectRow");
	$("#employee :input[id='updateButton']").val("Save New Employee");
	$("#employee :input[id='deleteButton']").prop('disabled',true);
	$("#employee :input[id='name']").focus();
}

/**
 * Populate form fields with data from a JSON
 * 
 * @param frm
 * @param data
 */
function populateFormJSON(frm, data) {
	  $.each(data, function(key, value){
	    $('[id='+key+']', frm).val(value);
	  });
	  if($("#employee :input[id='id']").val() > 0){
	       $("#employee :input[id='updateButton']").val("Save/Update Employee");
	       $("#employee :input[id='deleteButton']").prop('disabled', false);
	  } else {
		  $("#employee :input[id='updateButton']").val("Save New Employee");
		  $("#employee :input[id='deleteButton']").prop('disabled',true);
	  }
}


/**
 * Use the employee rest service to retrieve data for
 * the employee (by id) and populate the form field on this page
 * 
 * @param empid
 */
function populateFormWS(empid) {
	var uri = "/codetest/ws/employees/" + empid;
	$.ajax({
	      url: uri
	  }).then(function(data) {
		  populateFormJSON("#employee",data);
	  }); 
}


/**
 * Create a JSON object from the
 * "employee form" on this page
 * 
 * @returns JSON
 */
function getFormDataAsJSON(){	
	var json = {};
	jQuery.each($( "form#employee :input" ), function() {
		if(this.type != "button")
		  json[this.id] = this.value || '';
	});
	return json;
}


/**
 * Updates or creates the employee data on the server/cloud
 *  via PUTS or POSTS to employee/{id} resources
 */
function updateEmployeeWS() {
	var requestType;
	var uri = "/codetest/ws/employees";
	var employeeId = $("#employee :input[id='id']").val();
	var employee = getFormDataAsJSON();
	if(employeeId > 0) {
		requestType = "PUT";
		uri = uri + "/" + employeeId;
	} else {
		requestType = "POST";
	}	
    $.ajax({
        url: uri,
        processData : false,
        type :  requestType,
        contentType: "application/json",
        data: JSON.stringify(employee)
    })
    .done (function(data) { 
     	populateFormJSON("#employee",data);
      	loadTableData();
    	})
    .fail (function(jqxhr, textStatus, errorThrown)  { 
     	alert("Error " + textStatus) ; 
    	}); 
}

/**
 * Handles sending delete requests to the server.
 * 
 * SHAMELESS copy and past of the update function.
 * 
 */
function handleDelete() {
	var employeeId = $("#employee :input[id='id']").val();
	var uri = "/codetest/ws/employees"+"/" + employeeId;
	$.ajax({
        url: uri,
        processData : false,
        type : "DELETE",
        contentType: "application/json",
        data: JSON.stringify(employee)
    })
    .done (function(data) { 
     	clearFormForNew();
      	loadTableData();
    	})
    .fail (function(jqxhr, textStatus, errorThrown)  { 
     	alert("Error " + textStatus) ; 
    	    console.log(jqxhr.getAllResponseHeaders());
    	});
}
