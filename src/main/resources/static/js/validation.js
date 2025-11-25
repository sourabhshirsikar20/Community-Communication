document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('submit-block-form');
    const fields = [
        { el: document.getElementById('blockName'), min: 3, msg: 'at least 3 characters' },
        { el: document.getElementById('mainText'), min: 10, msg: 'at least 10 characters' },
        { el: document.getElementById('linksText'), min: 0, msg: '' },
        { el: document.getElementById('infoText'), min: 0, msg: '' },
        { el: document.getElementById('promoText'), min: 0, msg: '' }
    ];

    // Character counter
    function updateCharCount(field, counterId, maxLength) {
        const counter = document.getElementById(counterId);
        if (!counter) return;
        const len = field.value.length;
        counter.textContent = len;
        const parent = counter.parentElement;
        parent.classList.remove('warning', 'danger');
        if (len > maxLength * 0.9) parent.classList.add('danger');
        else if (len > maxLength * 0.75) parent.classList.add('warning');
    }

    // Real-time validation
    function validateField(fieldObj) {
        const field = fieldObj.el;
        const value = field.value.trim();
        const minLength = fieldObj.min;
        const msg = fieldObj.msg;

        let isValid = true;
        let errorMsg = '';

        if (minLength > 0 && value.length < minLength) {
            isValid = false;
            errorMsg = `Must be ${msg}`;
        }

        field.classList.remove('error', 'success');
        let feedback = field.nextElementSibling;
        if (!feedback || !feedback.classList.contains('feedback')) {
            feedback = document.createElement('div');
            feedback.className = 'feedback';
            field.parentNode.insertBefore(feedback, field.nextSibling);
        }

        if (value.length > 0) {
            if (isValid) {
                field.classList.add('success');
                feedback.textContent = '';
            } else {
                field.classList.add('error');
                feedback.textContent = errorMsg;
            }
        } else {
            feedback.textContent = '';
        }

        return isValid;
    }

    // Attach events
    fields.forEach(f => {
        if (!f.el) return;

        // Character counters for textareas with maxLength
        if (f.el.tagName === 'TEXTAREA') {
            const maxLen = f.el.dataset.maxlength || 256;
            f.el.addEventListener('input', () => updateCharCount(f.el, f.el.id + 'Count', maxLen));
            updateCharCount(f.el, f.el.id + 'Count', maxLen);
        }

        // Real-time validation
        f.el.addEventListener('input', () => validateField(f));
        f.el.addEventListener('blur', () => validateField(f));
    });

    // Form submit
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
            if (firstError) firstError.scrollIntoView({ behavior: 'smooth', block: 'center' });
        }
    });
});
