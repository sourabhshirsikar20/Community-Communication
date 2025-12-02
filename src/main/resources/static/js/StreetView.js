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

            let groups = svgElement.querySelectorAll('#fill_col');
            groups.forEach(function(g) {
                g.setAttribute('fill', color);
            });

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

document.addEventListener('DOMContentLoaded', () => {
    const searchInput = document.getElementById('search_value');
    const streetContainer = document.querySelector('.street-container');

    if (searchInput && streetContainer) {
        searchInput.addEventListener('input', async (e) => {
            const query = e.target.value;
            
            try {
                // Fetch results from the new API endpoint
                const response = await fetch(`/live-search?value=${encodeURIComponent(query)}`);
                if (!response.ok) throw new Error('Search failed');
                
                const blocks = await response.json();
                
                // Clear current blocks
                streetContainer.innerHTML = '';

                // Rebuild DOM for each block
                blocks.forEach(block => {
                    const link = document.createElement('a');
                    link.className = 'house-block';
                    link.href = `/OrganisationInfo/${block.id}`;
                    link.dataset.id = block.id;

                    // Helper to create layer divs
                    const createLayer = (type, option, color, text = null) => {
                        const div = document.createElement('div');
                        div.className = 'house-layer js-inject-svg';
                        div.dataset.url = `/svg/${type}/${type}-${option}.svg`;
                        div.dataset.color = color;
                        if (text) div.dataset.text = text;
                        return div;
                    };

                    // Append layers (Base, Roof, Foreground)
                    link.appendChild(createLayer('base', block.baseOption, block.baseColour, block.name));
                    link.appendChild(createLayer('roof', block.roofOption, block.roofColour));
                    link.appendChild(createLayer('foreground', block.foregroundOption, block.foregroundColour));

                    streetContainer.appendChild(link);
                });

                // Re-run SVG injection for the new elements
                injectSvgs();

            } catch (error) {
                console.error('Live search error:', error);
            }
        });
    }
});
