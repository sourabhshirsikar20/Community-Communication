// renderHeader();
// renderFooter();

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

            
            svgElement.style.fill = color;


            if (textToInject) {

                const existingText = svgElement.querySelector('text');
                if (existingText) {
                    existingText.textContent = textToInject;
                } 
            }

            container.appendChild(svgElement);

        } catch (error) {
            console.error('Error injecting SVG:', error);
        }
    }
}

injectSvgs();
