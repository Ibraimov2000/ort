import axiosInstance from "@/api/axiosInstance.js";

const examService = {

    createExam(createRequest) {
        return axiosInstance.post('/exams/create', createRequest, {
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.data)
            .catch(error => {
                console.error('Error creating exam: ', error);
                throw error;
            });
    },

    getExams() {
        return axiosInstance.get('/exams')
            .then(response => response.data)
            .catch(error => {
                console.error('Error fetching exams:', error);
                throw error;
            });
    },

    getExamById(testId) {
        return axiosInstance.get(`/exams/${testId}`)
            .then(response => response.data)
            .catch(error => {
                console.error('Error fetching exam by ID:', error);
                throw error;
            });
    },

    submitAnswers(answers) {
        return axiosInstance.post('/user-answers/submit', { answers })
            .then(response => response.data)
            .catch(error => {
                console.error('Error submitting answers:', error);
                throw error;
            });
    },

    getExamResults(testId) {
        return axiosInstance.get(`/exam-results/${testId}`)
            .then(response => response.data)
            .catch(error => {
                console.error('Error fetching exam results:', error);
                throw error;
            });
    }
};

export default examService;
