// Basic form validation before submission
function validateForm(formId) {
    const form = document.getElementById(formId);
    if (!form.checkValidity()) {
        alert("Please fill all required fields correctly.");
        return false;
    }
    return true;
}


function confirmAction(message = "Are you sure you want to proceed?") {
    return confirm(message);
}


function filterTable(inputId, tableId) {
    const input = document.getElementById(inputId);
    const filter = input.value.toLowerCase();
    const table = document.getElementById(tableId);
    const rows = table.getElementsByTagName("tr");

    for (let i = 1; i < rows.length; i++) {
        const rowText = rows[i].innerText.toLowerCase();
        rows[i].style.display = rowText.includes(filter) ? "" : "none";
    }
}


function showToast(message, duration = 3000) {
    const toast = document.createElement("div");
    toast.textContent = message;
    toast.style.position = "fixed";
    toast.style.bottom = "20px";
    toast.style.right = "20px";
    toast.style.backgroundColor = "#3498db";
    toast.style.color = "#fff";
    toast.style.padding = "12px 20px";
    toast.style.borderRadius = "8px";
    toast.style.boxShadow = "0 2px 8px rgba(0,0,0,0.2)";
    toast.style.zIndex = "9999";
    document.body.appendChild(toast);

    setTimeout(() => {
        document.body.removeChild(toast);
    }, duration);
}
