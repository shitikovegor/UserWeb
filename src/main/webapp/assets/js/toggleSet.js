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
}