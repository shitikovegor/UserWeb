$(document).ready(function() {

  var docHeight = $(window).height();
  var footerHeight = $('#footer').height();
  var footerTop = $('#footer').position().top + footerHeight;

  if (footerTop < docHeight)
    $('#footer').css('margin-top', 0+ (docHeight - footerTop) + 'px');
});

function toggleSet()
{
 if (document.getElementById("cargo").checked) {
        document.getElementById("cargo_set").style.display = 'block';
        document.getElementById("passenger_set").style.display = 'none';
    }
    else if (document.getElementById("passenger").checked) {
        document.getElementById("cargo_set").style.display = 'none';
        document.getElementById("passenger_set").style.display = 'block';
    }
    else {
        document.getElementById("cargo_set").style.display = 'none';
        document.getElementById("passenger_set").style.display = 'none';
    }
};

function dateValidation() {
    new Intl.DateTimeFormat()
    var date = document.querySelector('input[type="datetime-local"]');
    var dateNow = Date.now();
    date.min = new Date(dateNow).toDatetimeLocal();
}

Date.prototype.toDatetimeLocal =
    function toDatetimeLocal() {
        var
            date = this,
            ten = function (i) {
                return (i < 10 ? '0' : '') + i;
            },
            YYYY = date.getFullYear(),
            MM = ten(date.getMonth() + 1),
            DD = ten(date.getDate()),
            HH = ten(date.getHours()),
            II = ten(date.getMinutes())
        ;
        return YYYY + '-' + MM + '-' + DD + 'T' +
            HH + ':' + II;
    };

Date.prototype.fromDatetimeLocal = (function (BST) {
    // BST should not be present as UTC time
    return new Date(BST).toISOString().slice(0, 16) === BST ?
        // if it is, it needs to be removed
        function () {
            return new Date(
                this.getTime() +
                (this.getTimezoneOffset() * 60000)
            ).toISOString();
        } :
        // otherwise can just be equivalent of toISOString
        Date.prototype.toISOString;
}('2006-06-06T06:06'));

document.addEventListener('keydown', (event) => {
    if (event.keyCode === 116) event.preventDefault();
})