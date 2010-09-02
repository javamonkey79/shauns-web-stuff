function toggleContentDiv(divId) {
	var element = document.getElementById(divId);

	var siblingNodes = element.parentNode.getElementsByTagName("div");
	for ( var iNodes = 0; iNodes < siblingNodes.length; iNodes++) {
		if (siblingNodes[iNodes].className == 'innerContent') {
			siblingNodes[iNodes].style.visibility = "hidden";
		}
	}

	element.style.visibility = "visible";
}

function initializeMap() {
	var latLong = new google.maps.LatLng(47.676905, -117.303324);
	
	var mapOptions = {
		zoom : 13,
		center : latLong,
		mapTypeId : google.maps.MapTypeId.ROADMAP,
		streetViewControl : true
	};
	
	var map = new google.maps.Map(document.getElementById( "map_canvas" ),
			mapOptions);

	new google.maps.Marker( {
		position : latLong,
		map : map,
		title : "Guaranteed Appliance"
	});
}