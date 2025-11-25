/**
 * Fetches an SVG, colorizes it, injects text, and appends to the container.
 */
async function injectSvgs() {
    const containers = document.querySelectorAll('.js-inject-svg');

    for (const container of containers) {
        const url = container.getAttribute('data-url');
        const color = container.getAttribute('data-color');
        const textToInject = container.getAttribute('data-text');

        try {
            const response = await fetch(url);
            if (!response.ok) throw new Error(`Failed to load ${url}`);
            
            const svgText = await response.text();
            const parser = new DOMParser();
            const doc = parser.parseFromString(svgText, 'image/svg+xml');
            const svgElement = doc.querySelector('svg');

            // 1. Apply Colour
            // We set the 'fill' on the root SVG. 
            // Note: Ensure your SVG shapes use fill="currentColor" or have no fill set 
            // for this to work best. If outlines are strokes (stroke="black"), they will remain visible.
            svgElement.style.fill = color;

            // 2. Inject Block Name (Only for Base Layer)
            if (textToInject) {
                // Option A: Target an existing ID inside your SVG (e.g., <text id="house-label">)
                const existingText = svgElement.querySelector('text');
                if (existingText) {
                    existingText.textContent = textToInject;
                } 
                // Option B: Create a new text element if one doesn't exist
                else {
                    const newText = document.createElementNS("http://www.w3.org/2000/svg", "text");
                    newText.setAttribute("x", "50%");      // Center horizontally
                    newText.setAttribute("y", "80%");      // Position near bottom
                    newText.setAttribute("text-anchor", "middle");
                    newText.setAttribute("fill", "black"); // Text color
                    newText.setAttribute("font-size", "16px");
                    newText.setAttribute("font-weight", "bold");
                    newText.textContent = textToInject;
                    svgElement.appendChild(newText);
                }
            }

            container.appendChild(svgElement);

        } catch (error) {
            console.error('Error injecting SVG:', error);
        }
    }
}

// Run the injection
injectSvgs();