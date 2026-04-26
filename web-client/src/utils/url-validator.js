export const checkValidUrl = (input) => {
    let url;

    try {
        url = new URL(input);
    } catch (e) {
        console.log('Invalid url', e);
        return false;
    }

    return url.protocol === "http:" || url.protocol === "https:";
}
