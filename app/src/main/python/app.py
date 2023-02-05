import requests

def fun(TeacherID, Video ,Image ):
<<<<<<< HEAD
        jeeeson={"name":"test.mp4","data":Video}
        try:
            response = requests.post("https://umairmirza-face-attendance.hf.space/run/predict", json={
                "data": [
                    TeacherID,
                    jeeeson,
                    Image,
                ]
            }).json()

            data = response["data"]
            return data
        except Exception as e:
            return e
=======
        response = requests.post("https://umairmirza-face-attendance.hf.space/run/predict", json={
            "data": [
                TeacherID,
                Video,
                Image,
            ]
        }).json()

        data = response["data"]
        return data
>>>>>>> github/master


