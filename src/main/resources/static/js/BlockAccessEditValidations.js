document.addEventListener('DOMContentLoaded', function () {

    function smoothScrollToElement(el) {
        if (!el) return;
        const rect = el.getBoundingClientRect();
        const offset = rect.top + window.pageYOffset - 120;
        window.scrollTo({
            top: offset,
            behavior: 'smooth'
        });
    }

    function scrollToFirstError(scope) {
        const root = scope || document;
        const target = root.querySelector('.error, .invalid-field, .form-error');
        if (target) {
            smoothScrollToElement(target);
        }
    }

    function setError(input, errorElem, message) {
        if (input) {
            input.classList.add('error', 'invalid-field');
        }
        if (errorElem) {
            errorElem.textContent = message || '';
        }
    }

    function clearError(input, errorElem) {
        if (input) {
            input.classList.remove('error', 'invalid-field');
        }
        if (errorElem) {
            errorElem.textContent = '';
        }
    }

    // ========== WORD COUNT FUNCTIONS ==========
    function countWords(text) {
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
        
        if (wordCount > maxWords) {
            parent.classList.add('danger');
        } else if (wordCount > maxWords * 0.7) {
            parent.classList.add('warning');
        }
    }

    // ========== BLOCK ACCESS EDIT FORM ==========
    (function () {
        const form = document.getElementById('block-access-edit-form');
        if (!form) return;

        const mainText = document.getElementById('mainText');
        const linksText = document.getElementById('linksText');
        const infoText = document.getElementById('infoText');
        const promoText = document.getElementById('promoText');
        const addressField = document.getElementById('addressField');
        const addressError = document.getElementById('addressError');

        let mainTextFeedback = null;
        if (mainText) {
            const wrapper = mainText.closest('.form-field');
            if (wrapper) {
                mainTextFeedback = wrapper.querySelector('.feedback');
            }
        }

        const MIN_MAIN_CHARS = 10;
        const MAX_MAIN_WORDS = 512;
        const MAX_OTHER_WORDS = 256;

        const originalAddress = addressField ? addressField.value.trim() : null;

        // ========== UPDATE WORD COUNTERS ==========
        function updateAllCounters() {
            if (mainText) updateWordCount(mainText, 'mainTextCount', MAX_MAIN_WORDS);
            if (linksText) updateWordCount(linksText, 'linksTextCount', MAX_OTHER_WORDS);
            if (infoText) updateWordCount(infoText, 'infoTextCount', MAX_OTHER_WORDS);
            if (promoText) updateWordCount(promoText, 'promoTextCount', MAX_OTHER_WORDS);
        }

        function clearMainFeedback() {
            if (mainText) {
                mainText.classList.remove('error', 'invalid-field');
            }
            if (mainTextFeedback) {
                mainTextFeedback.textContent = '';
            }
        }

        function setMainFeedback(msg) {
            if (mainText) {
                mainText.classList.add('error', 'invalid-field');
            }
            if (mainTextFeedback) {
                mainTextFeedback.textContent = msg || '';
            }
        }

        function validateMainText() {
            if (!mainText) return true;
            const value = mainText.value.trim();
            const wordCount = countWords(value);
            clearMainFeedback();

            if (value.length === 0) {
                setMainFeedback('Main text may not be empty');
                return false;
            }
            if (value.length < MIN_MAIN_CHARS) {
                setMainFeedback(`Minimum ${MIN_MAIN_CHARS} characters required`);
                return false;
            }
            if (wordCount > MAX_MAIN_WORDS) {
                setMainFeedback(`Maximum ${MAX_MAIN_WORDS} words allowed (currently ${wordCount} words)`);
                return false;
            }
            return true;
        }

        function validateTextField(field, maxWords, fieldName) {
            if (!field) return true;
            const value = field.value.trim();
            const wordCount = countWords(value);
            
            if (wordCount > maxWords) {
                // Find or create feedback element
                let feedback = field.closest('.form-field')?.querySelector('.feedback');
                if (!feedback) {
                    feedback = document.createElement('div');
                    feedback.className = 'feedback error';
                    field.parentNode.appendChild(feedback);
                }
                feedback.textContent = `Maximum ${maxWords} words allowed (currently ${wordCount} words)`;
                field.classList.add('error', 'invalid-field');
                return false;
            } else {
                // Clear error
                const feedback = field.closest('.form-field')?.querySelector('.feedback');
                if (feedback) feedback.textContent = '';
                field.classList.remove('error', 'invalid-field');
                return true;
            }
        }

        function validateEditAddress() {
            if (!addressField) return true;
            const value = addressField.value.trim();
            clearError(addressField, addressError);

            if (value.length === 0) {
                setError(addressField, addressError, 'Address is required');
                return false;
            }
            return true;
        }

        // ========== EVENT LISTENERS ==========
        if (mainText) {
            mainText.addEventListener('input', function () {
                updateWordCount(mainText, 'mainTextCount', MAX_MAIN_WORDS);
                validateMainText();
            });
            mainText.addEventListener('blur', validateMainText);
        }

        if (linksText) {
            linksText.addEventListener('input', function () {
                updateWordCount(linksText, 'linksTextCount', MAX_OTHER_WORDS);
                validateTextField(linksText, MAX_OTHER_WORDS, 'Links');
            });
        }

        if (infoText) {
            infoText.addEventListener('input', function () {
                updateWordCount(infoText, 'infoTextCount', MAX_OTHER_WORDS);
                validateTextField(infoText, MAX_OTHER_WORDS, 'Info');
            });
        }

        if (promoText) {
            promoText.addEventListener('input', function () {
                updateWordCount(promoText, 'promoTextCount', MAX_OTHER_WORDS);
                validateTextField(promoText, MAX_OTHER_WORDS, 'Promo');
            });
        }

        if (addressField) {
            addressField.addEventListener('input', validateEditAddress);
            addressField.addEventListener('blur', validateEditAddress);
        }

        // Initialize counters on page load
        updateAllCounters();

        // ========== FORM SUBMISSION ==========
        form.addEventListener('submit', function (e) {
            e.preventDefault();
            (async () => {
                let ok = true;

                if (!validateMainText()) ok = false;
                if (!validateTextField(linksText, MAX_OTHER_WORDS, 'Links')) ok = false;
                if (!validateTextField(infoText, MAX_OTHER_WORDS, 'Info')) ok = false;
                if (!validateTextField(promoText, MAX_OTHER_WORDS, 'Promo')) ok = false;
                if (!validateEditAddress()) ok = false;

                if (ok && addressField) {
                    const newAddress = addressField.value.trim();

                    if (originalAddress && newAddress !== originalAddress && newAddress.length > 0) {
                        try {
                            const resp = await fetch('/BlockAccess/checkAddress?value=' + encodeURIComponent(newAddress));
                            if (resp.ok) {
                                const exists = await resp.json();
                                if (exists) {
                                    setError(addressField, addressError, 'Address already exists. Changes NOT saved.');
                                    ok = false;
                                }
                            }
                        } catch (err) {
                            console.error('Address check failed:', err);
                        }
                    }
                }

                if (!ok) {
                    scrollToFirstError(form);
                } else {
                    form.submit();
                }
            })();
        });

        scrollToFirstError(form);
    })();

    // ========== UPDATE CODE FORM ==========
    (function () {
        const form = document.getElementById('update-code-form');
        if (!form) return;

        const newCode = document.getElementById('newAccessCode');
        const confirmCode = document.getElementById('confirmAccessCode');
        const newCodeError = document.getElementById('newAccessCodeError');
        const confirmCodeError = document.getElementById('confirmAccessCodeError');

        function validateNewCode() {
            if (!newCode) return true;
            const value = newCode.value.trim();
            clearError(newCode, newCodeError);

            if (value.length === 0) {
                return true;
            }
            if (!/^\d{4,6}$/.test(value)) {
                setError(newCode, newCodeError, 'Code must be 4–6 digits');
                return false;
            }
            return true;
        }

        function validateConfirmCode() {
            if (!newCode || !confirmCode) return true;

            const newVal = newCode.value.trim();
            const confirmVal = confirmCode.value.trim();
            clearError(confirmCode, confirmCodeError);

            if (newVal.length === 0) {
                return true;
            }
            if (confirmVal.length === 0) {
                setError(confirmCode, confirmCodeError, 'Please repeat new code');
                return false;
            }
            if (newVal !== confirmVal) {
                setError(confirmCode, confirmCodeError, 'Codes do not match');
                return false;
            }
            return true;
        }

        newCode && newCode.addEventListener('input', function () {
            validateNewCode();
            validateConfirmCode();
        });
        confirmCode && confirmCode.addEventListener('input', validateConfirmCode);

        form.addEventListener('submit', function (e) {
            let ok = true;
            if (!validateNewCode()) ok = false;
            if (!validateConfirmCode()) ok = false;

            if (!ok) {
                e.preventDefault();
                scrollToFirstError(form);
            }
        });
    })();
});