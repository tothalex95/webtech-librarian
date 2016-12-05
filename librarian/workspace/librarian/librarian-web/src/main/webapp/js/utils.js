function convertDate(dateToCheck) {
	var date = new Date(dateToCheck);
	var turningDate = new Date(1582, 9, 15);
	if (date.getTime() < turningDate.getTime()) {
		date.setDate(date.getDate() - 10);
	}
	var century = 1500
	while (date.getFullYear() < century) {
		if ((century % 400) != 0) {
			date.setDate(date.getDate() + 1);
		}
		century = century - 100;
	}
	var day = date.getDate();
	var dayStr = day < 10 ? '0' + day : day;
	var month = date.getMonth() + 1;
	var monthStr = month < 10 ? '0' + month : month;
	var year = date.getYear() + 1900;
	var yearStr = year;
	var formatedTime = yearStr + '.' + monthStr + '.' + dayStr + '.';
	return formatedTime;
}

$.fn.serializeObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name] !== undefined) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
}

function getParameter(name) {
	if (name = (new RegExp('[?&]' + encodeURIComponent(name) + '=([^&]*)'))
			.exec(location.search))
		return decodeURIComponent(name[1]);
}

function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function getValidDateString(date) {
	var day = date.getDate();
	var dayStr = day < 10 ? '0' + day : day;
	var month = date.getMonth() + 1;
	var monthStr = month < 10 ? '0' + month : month;
	var year = date.getYear() + 1900;
	var yearStr = year;
	return yearStr + '-' + monthStr + '-' + dayStr;
}

function loadPage(target) {
	var splittedTarget = target.split(':');
	setCookie('id', splittedTarget[0], 1);
	$('#content').load(splittedTarget[1]);
}

function hideNotification() {
	$('.notification').hide();
}