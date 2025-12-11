document.addEventListener("DOMContentLoaded", function () {

    const input = document.getElementById("addressField");
    const errorBox = document.getElementById("addressError");

    if (!input || !errorBox) return;

    input.addEventListener("change", async function () {
        const value = this.value.trim();
        if (value === "") return;

        const response = await fetch("/BlockAccess/checkAddress?value=" + value);
        const taken = await response.text();

        if (taken === "true") {
            errorBox.textContent = "This address is already taken!";
            this.style.border = "2px solid red";
        } else {
            errorBox.textContent = "";
            this.style.border = "";
        }
    });
});
