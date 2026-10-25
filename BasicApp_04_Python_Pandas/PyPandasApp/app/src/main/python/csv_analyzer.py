# app/python/csv_analyzer.py
import pandas as pd
import io


def table(csv_content):
    """
    table - converts CSV into a DataFrame
    :param csv_content: CSV loaded (text)
    :return: first 15 rows of the dataframe
    """
    df: Any = pd.read_csv(io.StringIO(csv_content))
    return df.head(15).to_string()
