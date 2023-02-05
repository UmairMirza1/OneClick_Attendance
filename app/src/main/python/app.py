import requests

def fun(TeacherID, Video ,Image ):
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


