function PopUp(selector) {
	var popupStatus = 0;
	
	this.load = function() {
		if(popupStatus == 0){  
		    $("#backgroundPopup").css({  
		    "opacity": "0.7"  
		    });  
		    $("#backgroundPopup").fadeIn("slow");  
		    $(selector).fadeIn("slow");  
		    popupStatus = 1;  
	    }  
	};
	
	var disable = function() {
		if (popupStatus == 1){  
		    $("#backgroundPopup").fadeOut("slow");  
		    $(selector).fadeOut("slow");  
		    popupStatus = 0;  
	    }	
	};
	this.disable = disable;
	
	this.center = function() {
	    var windowWidth = document.documentElement.clientWidth;  
	    var windowHeight = document.documentElement.clientHeight;  
	    var popupHeight = $(selector).height();  
	    var popupWidth = $(selector).width();  
	    
	    $(selector).css({  
		    "position": "absolute",  
		    "top": windowHeight/2-popupHeight/2,  
		    "left": windowWidth/2-popupWidth/2  
	    });  

	    $("#backgroundPopup").css({  
	    	"height": windowHeight  
	    });	
	};
	
	$(selector +" .popup-close").click(function(){  
    	disable();  
    }); 
	
    $("#backgroundPopup").click(function(){  
    	disable();  
    });
    
    $(document).keypress(function(e){  
    	if (e.keyCode==27 && popupStatus == 1) {  
    		disable();  
    	}  
    });  
}