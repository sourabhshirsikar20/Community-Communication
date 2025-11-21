function enableTableInteractions(allEntries) {
    
    const displayBox = document.getElementById('info_display');
    const clickableItems = document.querySelectorAll('.entry_names');

    clickableItems.forEach(item => {
        item.addEventListener('click', function() {
            
            const idString = this.getAttribute('data-id');
            const id = parseInt(idString, 10); 

            console.log("Clicked ID (converted):", id);
            console.log("Type of ID:", typeof id);

            const selectedBlock = allEntries.find(block => block.id === id);

            console.log("Found Block:", selectedBlock);

            if (selectedBlock) {
                displayBox.innerHTML = `
                    <div class="details_pane">
                        <h3>${selectedBlock.name}</h3>
                        <p>${selectedBlock.mainText || "No details found."}</p>
                    </div>
                `;
            } else {
                console.warn("Could not find a block with ID:", id);
            }
        });
    });
}