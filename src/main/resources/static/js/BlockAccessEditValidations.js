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

    (function () {
        const form = document.getElementById('block-access-edit-form');
        if (!form) return;

        const mainText = document.getElementById('mainText');
        const mainTextCount = document.getElementById('mainTextCount');
        const addressField = document.getElementById('addressField');
        const addressError = document.getElementById('addressError');

        let mainTextFeedback = null;
        if (mainText) {
            const wrapper = mainText.closest('.form-field');
            if (wrapper) {
                mainTextFeedback = wrapper.querySelector('.feedback');
            }
        }

        const MIN_MAIN = 10;
        const MAX_MAIN = 512;

        const originalAddress = addressField ? addressField.value.trim() : null;

        function updateMainCount() {
            if (!mainText || !mainTextCount) return;
            mainTextCount.textContent = mainText.value.length;
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
            clearMainFeedback();

            if (value.length === 0) {
                setMainFeedback('Main text may not be empty');
                return false;
            }
            if (value.length < MIN_MAIN) {
                setMainFeedback('Minimum 10 characters!');
                return false;
            }
            if (value.length > MAX_MAIN) {
                setMainFeedback('Main text cannot exceed 512 characters');
                return false;
            }
            return true;
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

        if (mainText) {
            mainText.addEventListener('input', function () {
                updateMainCount();
                validateMainText();
            });
            mainText.addEventListener('blur', validateMainText);
            updateMainCount();
        }

        addressField && addressField.addEventListener('input', validateEditAddress);
        addressField && addressField.addEventListener('blur', validateEditAddress);

        form.addEventListener('submit', function (e) {
            e.preventDefault();
            (async () => {
                let ok = true;

                if (!validateMainText()) ok = false;
                if (!validateEditAddress()) ok = false;

                if (ok && addressField) {
                    const newAddress = addressField.value.trim();

                    if (originalAddress && newAddress !== originalAddress && newAddress.length > 0) {
                        try {
                            const resp = await fetch('/BlockAccess/checkAddress?value=' + encodeURIComponent(newAddress));
                            if (resp.ok) {
                                const exists = await resp.json(); // true / false
                                if (exists) {
                                    setError(addressField, addressError, 'Address already exists. Changes NOT saved.');
                                    ok = false;
                                }
                            }
                        } catch (err) {
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


    (function () {
        const form = document.getElementById('block-access-edit-form');
        if (!form) return;

        const mainText = document.getElementById('mainText');
        const mainTextCount = document.getElementById('mainTextCount');
        const addressField = document.getElementById('addressField');
        const addressError = document.getElementById('addressError');

        let mainTextFeedback = null;
        if (mainText) {
            const wrapper = mainText.closest('.form-field');
            if (wrapper) {
                mainTextFeedback = wrapper.querySelector('.feedback');
            }
        }

        const MIN_MAIN = 10;
        const MAX_MAIN = 512;

        function updateMainCount() {
            if (!mainText || !mainTextCount) return;
            mainTextCount.textContent = mainText.value.length;
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
            clearMainFeedback();

            if (value.length === 0) {
                setMainFeedback('Main text may not be empty');
                return false;
            }
            if (value.length < MIN_MAIN) {
                setMainFeedback('Minimum 10 characters!');
                return false;
            }
            if (value.length > MAX_MAIN) {
                setMainFeedback('Main text cannot exceed 512 characters');
                return false;
            }
            return true;
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

        if (mainText) {
            mainText.addEventListener('input', function () {
                updateMainCount();
                validateMainText();
            });
            mainText.addEventListener('blur', validateMainText);
            updateMainCount();
        }

        addressField && addressField.addEventListener('input', validateEditAddress);
        addressField && addressField.addEventListener('blur', validateEditAddress);

        form.addEventListener('submit', function (e) {
            let ok = true;
            if (!validateMainText()) ok = false;
            if (!validateEditAddress()) ok = false;

            if (!ok) {
                e.preventDefault();
                scrollToFirstError(form);
            }
        });

        scrollToFirstError(form);
    })();

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
