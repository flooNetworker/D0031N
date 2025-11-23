// --- INITIALIZATION ---
document.addEventListener('DOMContentLoaded', () => {
    populateCourseCodes();
});

let currentStudents = [];

// Available Grades for the dropdown
const grades = ["U", "G", "VG"];

// --- UTILITY FUNCTIONS ---

function showMessage(message, type = 'info') {
    const box = document.getElementById('messageBox');
    box.innerText = message;
    box.className = 'mt-4 p-3 rounded-lg text-sm'; // Reset classes
    
    if (type === 'success') {
        box.classList.add('bg-green-100', 'text-green-800');
    } else if (type === 'error') {
        box.classList.add('bg-red-100', 'text-red-800');
    } else {
        box.classList.add('bg-blue-100', 'text-blue-800');
    }
    box.classList.remove('hidden');
}

// --- API AND DATA HANDLING FUNCTIONS ---

async function populateCourseCodes() {
    const courseCodeSelect = document.getElementById('courseCode');
    try {
        const response = await fetch('/api/v1/canvas/coursecodes');
        if (!response.ok) {
            throw new Error('Could not fetch course codes.');
        }
        const courseCodes = await response.json();

        courseCodeSelect.innerHTML = '<option value="" disabled selected>Välj kurs...</option>';

        courseCodes.forEach(code => {
            const option = document.createElement('option');
            option.value = code;
            option.textContent = code;
            courseCodeSelect.appendChild(option);
        });

        courseCodeSelect.addEventListener('change', (event) => {
            const selectedCourse = event.target.value;
            if (selectedCourse) {
                populateModules(selectedCourse);
            }
        });

    } catch (error) {
        console.error('Error populating coursecodes:', error);
        courseCodeSelect.innerHTML = '<option value="" disabled selected>Kunde inte ladda kurser</option>';
    }
}

async function populateModules(courseCode) {
    const assignmentSelect = document.getElementById('assignment');
    assignmentSelect.innerHTML = '<option value="" disabled selected>Laddar uppgifter...</option>';

    try {
        const response = await fetch(`/api/v1/epokdb/modules/${courseCode}`);
        if (!response.ok) {
            throw new Error('Could not fetch modules for this course.');
        }
        const modules = await response.json();

        assignmentSelect.innerHTML = '<option value="" disabled selected>Välj uppgift...</option>'; // Clear loading text

        modules.forEach(module => {
            const option = document.createElement('option');
            option.value = module.code; // e.g., "006"
            option.textContent = `${module.code} - ${module.description}`; // e.g., "006 - projekt"
            assignmentSelect.appendChild(option);
        });

    } catch (error) {
        console.error('Error populating modules:', error);
        assignmentSelect.innerHTML = '<option value="" disabled selected>Inga uppgifter hittades</option>';
    }
}

// Fetches student and module data when the "Hämta studenter" button is clicked
async function loadData() {
    const courseCode = document.getElementById('courseCode').value;
    const moduleCode = document.getElementById('assignment').value;

    if (!courseCode || !moduleCode) {
        showMessage("Vänligen välj både kurskod och uppgift.", 'error');
        return;
    }

    showMessage(`Hämtar studenter för ${courseCode}...`, 'info');

    try {
        // Hämta listan med registrerade användarnamn för den valda kursen/modulen
        const usernamesResponse = await fetch(`/api/v1/canvas/usernames/${courseCode}/${moduleCode}`);
        if (!usernamesResponse.ok) throw new Error('Could not fetch usernames.');
        const usernames = await usernamesResponse.json();

        if (usernames.length === 0) {
            showMessage("Inga studenter hittades för den valda modulen.", 'info');
            renderStudentTable([]);
            return;
        }

        // Hämta studentdetaljer (via POST (ingen storleksbegränsning)) och modulbetyg (via GET) parallellt för effektivitet
        const [studentsResponse, gradesResponse] = await Promise.all([
            fetch('/api/v1/studentdb/students/by-usernames', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(usernames),
            }),
            fetch(`/api/v1/canvas/grades/${courseCode}/${moduleCode}`)
        ]);

        if (!studentsResponse.ok) throw new Error('Could not fetch student details.');
        if (!gradesResponse.ok) throw new Error('Could not fetch grades.');

        const enrolledStudents = await studentsResponse.json();
        const grades = await gradesResponse.json();
        
        currentStudents = enrolledStudents;

        // tabellen med hämtade studenter
        renderStudentTable(enrolledStudents, grades);
        showMessage(`Hittade ${enrolledStudents.length} studenter`, 'success');

    } catch (error) {
        console.error("Error loading data:", error);
        showMessage(error.message, 'error');
    }
}

function renderStudentTable(students, canvasGrades) {
    const tableBody = document.getElementById('studentTableBody');
    tableBody.innerHTML = '';

    if (!students || students.length === 0) {
        tableBody.innerHTML = `<tr><td colspan="4" class="px-6 py-4 text-center text-gray-500">Inga studenter att visa.</td></tr>`;
        return;
    }

    students.forEach(student => {
        const row = tableBody.insertRow();
        const canvasGrade = canvasGrades[student.username] || 'Ej betygsatt';

        // Namn + användarnamn och personnummer (hover)
        row.insertCell().innerHTML = `<div class="px-6 py-3 whitespace-nowrap text-sm font-medium text-gray-900" title="${student.personNumber}">${student.name} (${student.username})</div>`;
        
        //Canvas betyg
        row.insertCell().innerHTML = `<div class="px-6 py-3 whitespace-nowrap text-sm text-gray-500">${canvasGrade}</div>`;

        // Ladok betyg Dropdown
        const ladokGradeCell = row.insertCell();
        const ladokGradeSelect = document.createElement('select');
        ladokGradeSelect.id = `ladok-grade-${student.username}`;
        ladokGradeSelect.className = 'p-2 border border-gray-300 rounded-lg text-sm w-full';

        const placeholderOption = document.createElement('option');
        placeholderOption.value = "";
        placeholderOption.textContent = "Välj betyg...";
        placeholderOption.disabled = true;
        placeholderOption.selected = true;
        ladokGradeSelect.appendChild(placeholderOption);

        grades.forEach(g => {
            const option = document.createElement('option');
            option.value = g;
            option.text = g;
            ladokGradeSelect.appendChild(option);
        });
        ladokGradeCell.appendChild(ladokGradeSelect);
        
        // Datum input
        const dateCell = row.insertCell();
        const dateInput = document.createElement('input');
        dateInput.type = 'date';
        dateInput.id = `date-${student.username}`;
        dateInput.className = 'p-2 border border-gray-300 rounded-lg text-sm w-full';

        dateCell.appendChild(dateInput);
    });
}

// Sparar resultat i ladok när "Registrera studieresultat i ladok" knappen klickas
async function handleSave() {
    const courseCode = document.getElementById('courseCode').value;
    const moduleCode = document.getElementById('assignment').value;

    if (!currentStudents || currentStudents.length === 0) {
        showMessage("Inga studenter att registrera. Hämta studenter först.", 'error');
        return;
    }

    showMessage("Registrerar betyg i Ladok...", 'info');

    // Håll reda på hur många som sparats, hoppats över och misslyckats
    let savedCount = 0;
    let updatedCount = 0;
    let skippedCount = 0;
    let errorCount = 0;

    for (const student of currentStudents) {
        const ladokGrade = document.getElementById(`ladok-grade-${student.username}`).value;
        const date = document.getElementById(`date-${student.username}`).value;
        const studentName = student.name;
        
        // Hoppa över studenter om inget betyg och inget datum är valt
        if (!ladokGrade && !date || ladokGrade && !date || !ladokGrade && date) {
            skippedCount++;
            continue;
        }

        try {
            // Kolla först om studenten redan finns i Ladok
            const existingResponse = await fetch(`/api/v1/ladokdb/ladok/${courseCode}/${student.personNumber}/${moduleCode}`);
            
            let response;
            let resultText;

            if (existingResponse.ok) {
                // Studenten finns redan - kolla om betyget har ändrats
                const existingData = await existingResponse.json();
                
                if (existingData.grade !== ladokGrade || existingData.date !== date) {
                    // Betyget eller datumet har ändrats - uppdatera med PUT
                    response = await fetch(`/api/v1/ladokdb/ladok/${courseCode}/${student.personNumber}/${moduleCode}/${date}/${ladokGrade}`, {
                        method: 'PUT'
                    });
                    resultText = await response.text();
                } else {
                    // Betyget är samma - skippa
                    skippedCount++;
                    continue;
                }
            } else {
                // Studenten finns inte - registrera ny med POST
                response = await fetch(`/api/v1/ladokdb/ladok/${courseCode}/${student.personNumber}/${moduleCode}/${date}/${ladokGrade}`, {
                    method: 'POST'
                });
                resultText = await response.text();
            }

            if (!response.ok) {
                showMessage(`Fel för ${studentName}: ${resultText}`, 'error');
                errorCount++;
                throw new Error(resultText || 'Serverfel');
            }
            
            if (resultText === "Registrerad") {
                savedCount++;
            } else if (resultText === "Uppdaterad") {
                updatedCount++;
            } else {
                errorCount++;
                throw new Error('Registrering hindrad');
            }

        } catch (error) {
            console.error(`Misslyckades att spara för ${student.username}:`, error);
        }
    }

    let summaryParts = [];
    if (savedCount > 0) summaryParts.push(`${savedCount} ${savedCount === 1 ? 'registrerad' : 'registrerade'}`);
    if (updatedCount > 0) summaryParts.push(`${updatedCount} ${updatedCount === 1 ? 'uppdaterad' : 'uppdaterade'}`);
    if (skippedCount > 0) summaryParts.push(`${skippedCount} ${skippedCount === 1 ? 'skippad' : 'skippade'}`);
    if (errorCount > 0) summaryParts.push(`${errorCount} ${errorCount === 1 ? 'hindrad' : 'hindrade'}`);
    
    const summaryMessage = `Registrering slutförd: ${summaryParts.join(', ')}.`;
    const messageType = errorCount > 0 ? 'error' : 'success';
    showMessage(summaryMessage, messageType);
    
}