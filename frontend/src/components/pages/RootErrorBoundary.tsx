import { isRouteErrorResponse, useRouteError } from "react-router";

export const RootErrorBoundary = () => {
  const error = useRouteError();
  const goBackButton = (
    <button
      type="button"
      onClick={() => window.history.back()}
      className="btn btn-primary mt-4"
    >
      Go Back
    </button>
  );

  if (isRouteErrorResponse(error)) {
    return (
      <div className="text-center bg-amber-100 p-25 rounded">
        <h1 className="text-3xl font-bold text-center my-5 text-amber-600">
          {error.status} {error.statusText}
        </h1>
        <p className="text-center my-4">{error.data}</p>
        {goBackButton}
      </div>
    );
  } else if (error instanceof Error) {
    return (
      <div className="text-center my-5">
        <h1 className="text-3xl font-bold text-center my-5">Error</h1>
        <p className="text-center my-4">{error.message}</p>
        <p className="text-center my-4">The stack trace is:</p>
        <pre className="text-center my-4">{error.stack}</pre>
        {goBackButton}
      </div>
    );
  } else {
    return (
      <div className="text-center my-5">
        <h1 className="text-3xl font-bold text-center my-5">Unknown Error</h1>
        {goBackButton}
      </div>
    );
  }
};
