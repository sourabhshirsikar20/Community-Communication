document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('submit-block-form');
    const fields = [
        { 
            el: document.getElementById('blockName'), 
            minChars: 3,
            required: true,
            type: 'text'
        },
        { 
            el: document.getElementById('mainText'), 
            minChars: 10,  
            maxWords: 512, 
            required: true,
            type: 'textarea'
        },
        { 
            el: document.getElementById('linksText'), 
            maxWords: 256,  
            required: false,
            type: 'textarea'
        },
        { 
            el: document.getElementById('infoText'), 
            maxWords: 256,
            required: false,
            type: 'textarea'
        },
        { 
            el: document.getElementById('promoText'), 
            maxWords: 256,
            required: false,
            type: 'textarea'
        }
    ];

    function countWords(text) {
        // Remove extra whitespace and split by spaces
        const trimmed = text.trim();
        if (trimmed.length === 0) return 0;
        return trimmed.split(/\s+/).length;
    }
    
    function updateWordCount(field, counterId, maxWords) {
        const counter = document.getElementById(counterId);
        if (!counter) return;
        
        const wordCount = countWords(field.value);
        counter.textContent = wordCount;
        
        const parent = counter.parentElement;
        parent.classList.remove('warning', 'danger');
        
        // Show warnings at 70% and danger at 100%
        if (wordCount > maxWords) {
            parent.classList.add('danger');
        } else if (wordCount > maxWords * 0.7) {
            parent.classList.add('warning');
        }
    }

    function validateField(fieldObj) {
        const field = fieldObj.el;
        if (!field) return true;
        
        const value = field.value.trim();
        const required = fieldObj.required;
        
        let isValid = true;
        let errorMsg = '';

        // CHARACTER-BASED validation (Block Name minimum, Main Text minimum)
        if (fieldObj.minChars !== undefined) {
            if (required && value.length < fieldObj.minChars) {
                isValid = false;
                errorMsg = `Must be at least ${fieldObj.minChars} characters`;
            }
        }

        // WORD-BASED validation (All textareas maximum)
        if (fieldObj.maxWords !== undefined) {
            const wordCount = countWords(value);
            if (wordCount > fieldObj.maxWords) {
                isValid = false;
                errorMsg = `Maximum ${fieldObj.maxWords} words allowed (currently ${wordCount} words)`;
            }
        }

        field.classList.remove('error', 'success');

        let feedback = field.closest('.form-field')?.querySelector('.feedback');
        if (!feedback) {
            feedback = document.createElement('div');
            feedback.className = 'feedback';
            const formField = field.closest('.form-field');
            if (formField) {
                formField.appendChild(feedback);
            }
        }

        if (required || value.length > 0) {
            if (!isValid) {
                field.classList.add('error');
                if (feedback) feedback.textContent = errorMsg;
            } else if (value.length > 0) {
                field.classList.add('success');
                if (feedback) feedback.textContent = '';
            } else {
                if (feedback) feedback.textContent = '';
            }
        }

        return isValid;
    }

    // Attach events
    fields.forEach(f => {
        if (!f.el) return;

        // For textareas, update word count on input
        if (f.type === 'textarea' && f.maxWords) {
            f.el.addEventListener('input', () => {
                updateWordCount(f.el, f.el.id + 'Count', f.maxWords);
                validateField(f);
            });
            // Initialize word count display
            updateWordCount(f.el, f.el.id + 'Count', f.maxWords);
        } else {
            // For text inputs, just validate
            f.el.addEventListener('input', () => validateField(f));
        }

        f.el.addEventListener('blur', () => validateField(f));
    });

    form.addEventListener('submit', function(e) {
        let allValid = true;
        
        fields.forEach(f => {
            if (!f.el) return;
            const valid = validateField(f);
            if (!valid) allValid = false;
        });

        if (!allValid) {
            e.preventDefault();
            const firstError = form.querySelector('.error');
            if (firstError) {
                firstError.scrollIntoView({ behavior: 'smooth', block: 'center' });
            }
        }
    });
});
