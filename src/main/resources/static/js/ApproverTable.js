function enableTableInteractions(allEntries) {
    
    const displayBox = document.getElementById('info-display');
    const clickableItems = document.querySelectorAll('.clickable-name');

    clickableItems.forEach(item => {
        item.addEventListener('click', function() {
            
            // --- THE FIX IS HERE ---
            // 1. Get the string and force it to be an Integer (base 10)
            const idString = this.getAttribute('data-id');
            const id = parseInt(idString, 10); 

            // Debugging: Check the console to see if this prints what you expect
            console.log("Clicked ID (converted):", id);
            console.log("Type of ID:", typeof id);

            // 2. Find the matching object
            // Now we can use === safely because both are numbers
            const selectedBlock = allEntries.find(block => block.id === id);

            // Debugging: Check if we actually found it
            console.log("Found Block:", selectedBlock);

            // 3. Update the display
            if (selectedBlock) {
                displayBox.innerHTML = `
                    <div class="card">
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