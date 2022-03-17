const selectPass = document.getElementById('selectPass');
const priceElement = document.getElementById('passPrice');
const expiryElement = document.getElementById('passExpiry');

selectPass.addEventListener('change', (ev) => {
    let element = ev.target;

    if(element.selectedIndex > 0) {
        let selectedElement = document.getElementsByTagName('option')[element.selectedIndex];
        priceElement.innerHTML = selectedElement.dataset.price + " zł";
        expiryElement.innerHTML = selectedElement.dataset.expiry + " miesiecy";
    }
})