const menuData = [
    {text: "Home", link: "home"},
    {text: "About us", link: "about"},
    {text: "Contact", link: "contact"}
];
function renderHeader() {
    const headerContainer = document.getElementById('header');
    headerContainer.innerHTML = `    
    <header class = "header">
            <div class= "header_Container">
                <div class = "logo"></div>
                <nav class = "header_Nav"></nav>
                <a class="">Local Organisers</a>
            </div>
            </header>
            `;

}
function renderFooter() {
    const footerContainer = document.getElementById('footer');
    footerContainer.innerHTML = `
    <footer class = "footer">
    <div class = "footer_Container">
    <div class = "footer_Nav"></div>
  
</div>
  <div class="copy_Rights">© ${new Date().getFullYear()}Streete. All rights reserved.</div>       
</footer>`;
}