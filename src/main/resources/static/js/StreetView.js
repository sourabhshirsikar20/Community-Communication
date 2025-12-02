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
                    existingText.setAttribute('fill', container.getAttribute('data-fontColour'));

                    // set font options
                    let fontOptions = container.getAttribute('data-fontOptions');
                    if (fontOptions) {
                        let style = JSON.parse(fontOptions);
                        if (style['serif']) {
                            existingText.style.fontFamily = '"Roboto serif", serif';
                            existingText.style.fontVariationSettings = `'wght' ${style['weight']}, 'wdth' ${style['width']}, 'GRAD' ${style['grade']}`;
                        }
                        else {
                            existingText.style.fontFamily = '"Roboto", sans-serif';
                            existingText.style.fontVariationSettings = `'wght' ${style['weight']}, 'wdth' ${style['width']}`;
                        }

                        existingText.style.fontStyle = style['italic'] ? 'italic' : 'normal';
                        existingText.style.letterSpacing = `${(style['width']-75)/(25)*(0.15)+0.05}em`;
                    }
                } 
            }

            container.appendChild(svgElement);

        } catch (error) {
            console.error('Error injecting SVG:', error);
        }
    }
}


injectSvgs();
