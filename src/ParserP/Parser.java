//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "gramaticaULTIMA.y"
 package ParserP; 
import AnalizadorLexico.ArchController;
import AnalizadorLexico.Token;
//#line 21 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short S_MAYOR_IGUAL=257;
public final static short S_MENOR_IGUAL=258;
public final static short S_DISTINTO=259;
public final static short CTE_D=260;
public final static short CTE_USLINTEGER=261;
public final static short COMENTARIO=262;
public final static short CADENA=263;
public final static short ID=264;
public final static short IF=265;
public final static short THEN=266;
public final static short ELSE=267;
public final static short END_IF=268;
public final static short DO=269;
public final static short FUN=270;
public final static short DOUBLE=271;
public final static short USLINTEGER=272;
public final static short RETURN=273;
public final static short PRINT=274;
public final static short ASIGNACION=275;
public final static short CASE=276;
public final static short VOID=277;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    1,    3,    3,    4,    4,    4,    2,
    2,    2,    7,    7,    5,    5,    9,    9,    9,   11,
   10,   10,   13,   13,   13,   13,   13,   13,   12,   12,
    8,    8,    6,    6,    6,    6,   16,   16,   18,   18,
   18,   14,   14,   20,   20,   20,   20,   20,   20,   20,
   19,   19,   21,   21,   23,   22,   22,   24,   26,   27,
   15,   28,   30,   31,   17,   17,   32,   32,   34,   29,
   29,   29,   33,   33,   33,   35,   35,   37,   35,   36,
   38,   40,   25,   39,   39,   39,   39,   39,   39,
};
final static short yylen[] = {                            2,
    1,    1,    3,    1,    1,    2,    3,    3,    3,    2,
    2,    1,    2,    2,    2,    1,    3,    1,    1,    2,
    8,    1,    7,    7,    7,    7,    6,    6,    1,    1,
    1,    1,    1,    1,    1,    1,    4,    1,    3,    3,
    2,    7,    1,    6,    6,    6,    6,    5,    3,    5,
    2,    1,    4,    1,    4,    1,    1,    0,    0,    0,
   11,    0,    0,    0,    6,    1,    2,    2,    0,    4,
    3,    1,    3,    3,    1,    1,    1,    0,    2,    2,
    0,    0,    5,    1,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,   58,   29,   32,   31,    0,    0,    0,   30,
    0,    0,    1,    2,    4,    0,    0,   12,    0,   16,
    0,   22,   33,   34,   35,   36,   38,   43,   66,    5,
    0,   56,   57,   78,    0,   76,    0,    0,    0,   75,
   77,    0,    0,    0,    0,    0,    0,    0,   13,   10,
   14,   11,   18,    0,   19,    0,    8,    7,    6,   79,
   80,   63,    0,    0,    0,    0,   81,   39,    0,    0,
    0,    0,   52,    0,   54,    0,    9,    3,   20,    0,
    0,    0,    0,    0,    0,    0,   73,   74,    0,    0,
   37,    0,    0,   51,    0,    0,    0,    0,   17,    0,
    0,    0,    0,    0,   70,   59,    0,   48,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   65,
    0,    0,   44,   55,   53,   45,    0,   46,   28,    0,
    0,   27,    0,    0,    0,   87,   88,   89,   84,   85,
   86,    0,   42,   24,   26,    0,   25,   23,    0,    0,
   21,    0,   60,   61,
};
final static short yydgoto[] = {                         12,
   13,   30,   31,   15,   16,   17,   18,   19,   54,   20,
   55,   21,   22,   23,   24,   25,   26,   27,   72,   28,
   73,   74,   75,   42,   89,  121,  154,   37,   38,   84,
  120,   29,   39,  105,   40,   41,   60,   90,  142,  122,
};
final static short yysindex[] = {                      -117,
  160,  -44,    0,    0,    0,    0,  -38,  -44,  -40,    0,
  160,    0,    0,    0,    0,  -41,  -35,    0, -245,    0,
 -235,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   10,    0,    0,    0, -211,    0, -217,   26,    5,    0,
    0,   32,   36, -184,   26,  -34, -183,   27,    0,    0,
    0,    0,    0,  -55,    0,   13,    0,    0,    0,    0,
    0,    0,  -44,  -44,  -44,  -44,    0,    0,   45, -199,
  -36, -199,    0,  -53,    0,  -31,    0,    0,    0, -176,
  160,  -29,  -32,  -44,    5,    5,    0,    0,   49,  -44,
    0, -111, -199,    0, -174, -173, -199, -115,    0,   71,
  160,  -99,  160,   26,    0,    0,   26,    0,  -97, -117,
 -117,  -77, -199,  -68, -180,   85,  160,   42,  107,    0,
  160,    3,    0,    0,    0,    0,  -66,    0,    0, -170,
   57,    0, -169, -168,  132,    0,    0,    0,    0,    0,
    0,  -44,    0,    0,    0, -167,    0,    0,  160,   26,
    0,  146,    0,    0,
};
final static short yyrindex[] = {                         0,
    0, -217,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -33,  -28,    0,
    0,    0,  -14,    0,  -13,    0,    0,    0,    0,    0,
    0,    0,    0,   -9,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   -8,    0,
    0,   -4,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -23,  -18,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   -3,    0,    0,    6,    0,    0,    0,
    0,    0,    0,    1,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    2,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   58,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  -37,   70,  284,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -15,    0,
  -12,   48,    0,    0,    0,    0,    0,    0,   43,    0,
    0,    0,   12,    0,   19,    0,    0,    0,    0,    0,
};
final static int YYTABLESIZE=437;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         47,
   35,   44,   50,   80,   96,   11,   71,  113,   52,   98,
   67,  102,   72,  108,   72,   72,   72,   69,   53,   69,
   69,   69,   71,  117,   71,   71,   71,  123,   56,   41,
   68,   72,   72,   72,   15,   40,   69,   69,   69,   49,
   64,   71,   71,   71,   50,   47,   65,  126,   61,   36,
   45,   66,   82,   83,   92,   36,  128,   62,  143,   94,
   32,   33,  139,  141,  140,   82,   82,   82,   63,   14,
   64,   67,  124,  125,   85,   86,   68,  109,   69,   94,
   76,  112,  114,   87,   88,   91,   93,   99,   70,  106,
  103,   97,  129,  101,  110,  111,   94,  127,   83,   94,
   59,   94,  144,  147,  148,  151,    0,    0,    0,    0,
   36,   36,   36,   36,   94,    0,    0,   59,    0,    0,
    0,    0,    0,    0,    0,    0,  104,    0,    0,    0,
    0,   36,  107,    0,   58,   81,    0,   36,    1,    0,
    0,    0,    0,    0,   32,   33,    2,    3,   32,   33,
    0,   78,    4,    5,    6,    0,    7,    8,    9,   10,
    0,    0,   32,   33,    2,    3,  133,    0,    0,   59,
    4,    5,    6,    0,    7,    8,    9,   10,    0,   14,
   14,  146,   32,   33,  150,   59,    0,   59,   59,   36,
    0,   32,   33,   32,   33,  115,    0,    0,    0,    0,
   59,    0,   95,    0,   59,    0,    0,    0,   79,  130,
    0,    0,    0,    0,   49,   32,   33,    0,    0,   34,
   51,   59,   67,   46,   43,   32,   33,   72,   72,   72,
   72,  134,   69,   69,   69,   69,    0,   71,   71,   71,
   71,   41,   68,    0,    0,    0,   15,   40,    0,    0,
    0,   49,   64,    0,    0,    0,   50,   47,    0,  136,
  137,  138,   82,   82,   82,   57,    0,    0,    0,    0,
    0,    0,    0,    2,    3,    0,    0,    0,    0,    4,
    5,    6,   77,    7,    8,    9,   10,    0,    0,    0,
    2,    3,    0,    0,   48,    0,    4,    5,    6,    0,
    7,    8,    9,   10,    0,    2,    3,    0,    0,    0,
    0,    4,    5,    6,  132,    7,    8,    9,   10,    0,
    2,    3,    0,    0,    0,    0,    4,    5,    6,  145,
    7,    8,    9,   10,    2,    3,    0,    0,    0,    0,
    4,    5,    6,    0,    7,    8,    9,   10,    2,    3,
    0,    0,    0,    0,    4,    5,    6,    0,    7,    8,
    9,   10,    0,    0,  100,    0,    0,    0,    0,    0,
    2,    3,    0,    0,    0,    0,    4,    5,    6,    0,
    7,    8,    9,   10,  116,  118,  119,    0,    0,    0,
    0,    0,    0,    0,    0,    2,    3,    0,  149,    0,
  131,    4,    5,    6,  135,    7,    8,    9,   10,    2,
    3,    0,    0,  153,    0,    4,    5,    6,    0,    7,
    8,    9,   10,    2,    3,    0,    0,    0,    0,    4,
    5,    6,  152,    7,    8,    9,   10,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   45,   40,   44,   59,   58,  123,   41,  123,   44,   41,
   44,   41,   41,  125,   43,   44,   45,   41,  264,   43,
   44,   45,   41,  123,   43,   44,   45,  125,  264,   44,
   44,   60,   61,   62,   44,   44,   60,   61,   62,   44,
   44,   60,   61,   62,   44,   44,   42,  125,  260,    2,
    8,   47,   40,   41,   70,    8,  125,  275,  125,   72,
  260,  261,   60,   61,   62,   60,   61,   62,   43,    0,
   45,   40,  110,  111,   63,   64,   41,   93,  263,   92,
  264,   97,   98,   65,   66,   41,  123,  264,  123,   41,
  123,  123,  273,  123,  269,  269,  109,  113,   41,  112,
   31,  114,  273,  273,  273,  273,   -1,   -1,   -1,   -1,
   63,   64,   65,   66,  127,   -1,   -1,   48,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   84,   -1,   -1,   -1,
   -1,   84,   90,   -1,  125,  123,   -1,   90,  256,   -1,
   -1,   -1,   -1,   -1,  260,  261,  264,  265,  260,  261,
   -1,  125,  270,  271,  272,   -1,  274,  275,  276,  277,
   -1,   -1,  260,  261,  264,  265,  125,   -1,   -1,  100,
  270,  271,  272,   -1,  274,  275,  276,  277,   -1,  110,
  111,  125,  260,  261,  142,  116,   -1,  118,  119,  142,
   -1,  260,  261,  260,  261,  125,   -1,   -1,   -1,   -1,
  131,   -1,  256,   -1,  135,   -1,   -1,   -1,  264,  125,
   -1,   -1,   -1,   -1,  256,  260,  261,   -1,   -1,  264,
  256,  152,  256,  264,  263,  260,  261,  256,  257,  258,
  259,  125,  256,  257,  258,  259,   -1,  256,  257,  258,
  259,  256,  256,   -1,   -1,   -1,  256,  256,   -1,   -1,
   -1,  256,  256,   -1,   -1,   -1,  256,  256,   -1,  257,
  258,  259,  257,  258,  259,  256,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  264,  265,   -1,   -1,   -1,   -1,  270,
  271,  272,  256,  274,  275,  276,  277,   -1,   -1,   -1,
  264,  265,   -1,   -1,   11,   -1,  270,  271,  272,   -1,
  274,  275,  276,  277,   -1,  264,  265,   -1,   -1,   -1,
   -1,  270,  271,  272,  273,  274,  275,  276,  277,   -1,
  264,  265,   -1,   -1,   -1,   -1,  270,  271,  272,  273,
  274,  275,  276,  277,  264,  265,   -1,   -1,   -1,   -1,
  270,  271,  272,   -1,  274,  275,  276,  277,  264,  265,
   -1,   -1,   -1,   -1,  270,  271,  272,   -1,  274,  275,
  276,  277,   -1,   -1,   81,   -1,   -1,   -1,   -1,   -1,
  264,  265,   -1,   -1,   -1,   -1,  270,  271,  272,   -1,
  274,  275,  276,  277,  101,  102,  103,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  264,  265,   -1,  267,   -1,
  117,  270,  271,  272,  121,  274,  275,  276,  277,  264,
  265,   -1,   -1,  268,   -1,  270,  271,  272,   -1,  274,
  275,  276,  277,  264,  265,   -1,   -1,   -1,   -1,  270,
  271,  272,  149,  274,  275,  276,  277,
};
}
final static short YYFINAL=12;
final static short YYMAXTOKEN=277;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,"':'","';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"S_MAYOR_IGUAL","S_MENOR_IGUAL","S_DISTINTO",
"CTE_D","CTE_USLINTEGER","COMENTARIO","CADENA","ID","IF","THEN","ELSE","END_IF",
"DO","FUN","DOUBLE","USLINTEGER","RETURN","PRINT","ASIGNACION","CASE","VOID",
};
final static String yyrule[] = {
"$accept : programa",
"programa : bloque",
"bloque : sentencia",
"bloque : '{' bloque_sentencias '}'",
"bloque : error_bloque",
"bloque_sentencias : sentencia",
"bloque_sentencias : bloque_sentencias sentencia",
"error_bloque : error bloque_sentencias '}'",
"error_bloque : error bloque_sentencias error",
"error_bloque : '{' bloque_sentencias error",
"sentencia : declaracion ','",
"sentencia : ejecucion ','",
"sentencia : error_sentencia_d",
"error_sentencia_d : declaracion error",
"error_sentencia_d : ejecucion error",
"declaracion : tipo lista_de_variables",
"declaracion : clousure",
"lista_de_variables : lista_de_variables ';' ID",
"lista_de_variables : ID",
"lista_de_variables : error_lista_de_variables",
"error_lista_de_variables : lista_de_variables ID",
"clousure : tipofuncion ID '(' ')' '{' bloque_sentencias '}' RETURN",
"clousure : error_clousure",
"error_clousure : tipofuncion ID ')' '{' bloque_sentencias '}' RETURN",
"error_clousure : tipofuncion ID '(' '{' bloque_sentencias '}' RETURN",
"error_clousure : tipofuncion ID '(' ')' bloque_sentencias '}' RETURN",
"error_clousure : tipofuncion ID '(' ')' '{' bloque_sentencias RETURN",
"error_clousure : tipofuncion ID '(' ')' bloque_sentencias RETURN",
"error_clousure : tipofuncion ID '{' bloque_sentencias '}' RETURN",
"tipofuncion : FUN",
"tipofuncion : VOID",
"tipo : USLINTEGER",
"tipo : DOUBLE",
"ejecucion : control",
"ejecucion : seleccion",
"ejecucion : print",
"ejecucion : asignacion",
"print : PRINT '(' CADENA ')'",
"print : error_print",
"error_print : PRINT CADENA ')'",
"error_print : PRINT '(' CADENA",
"error_print : PRINT CADENA",
"control : CASE '(' ID ')' '{' lista_acciones '}'",
"control : error_control",
"error_control : CASE ID ')' '{' lista_acciones '}'",
"error_control : CASE '(' ID '{' lista_acciones '}'",
"error_control : CASE '(' ID ')' lista_acciones '}'",
"error_control : CASE '(' ID ')' '{' lista_acciones",
"error_control : CASE ID '{' lista_acciones '}'",
"error_control : CASE ID lista_acciones",
"error_control : CASE '(' ID ')' lista_acciones",
"lista_acciones : lista_acciones accion",
"lista_acciones : accion",
"accion : cte ':' DO bloque",
"accion : error_accion",
"error_accion : cte error DO bloque",
"cte : CTE_D",
"cte : CTE_USLINTEGER",
"$$1 :",
"$$2 :",
"$$3 :",
"seleccion : IF $$1 '(' condicion ')' $$2 bloque_sentencias ELSE bloque_sentencias END_IF $$3",
"$$4 :",
"$$5 :",
"$$6 :",
"asignacion : ID $$4 ASIGNACION $$5 expresion $$6",
"asignacion : error_asignacion",
"error_asignacion : ID expresion",
"error_asignacion : ASIGNACION expresion",
"$$7 :",
"expresion : expresion '+' termino $$7",
"expresion : expresion '-' termino",
"expresion : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : cte",
"factor : factor_negado",
"$$8 :",
"factor : ID $$8",
"factor_negado : '-' CTE_D",
"$$9 :",
"$$10 :",
"condicion : $$9 expresion $$10 comparador expresion",
"comparador : '<'",
"comparador : '>'",
"comparador : '='",
"comparador : S_MAYOR_IGUAL",
"comparador : S_MENOR_IGUAL",
"comparador : S_DISTINTO",
};

//#line 212 "gramaticaULTIMA.y"





private ArchController lexico;
public Parser(ArchController lexico)
{
  this.lexico= lexico;
} 

public int yylex(){
    Token token = this.lexico.getToken();
   if(token != null ){ 
    int val =token.getTipo();
    yyval = new ParserVal(token);
    return val;
}
   else return 0;
}

public void yyerror(String s){
    System.out.println("Parser: " + s);
}


//#line 444 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 7:
//#line 45 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("en la linea "+" (aca va el numero de la linea)"+" Error sintactico: falta '{' ");}
break;
case 8:
//#line 46 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+" Error sintactico: falta '{'y '}' ");}
break;
case 9:
//#line 47 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError( "en la linea "+"(aca va el numero de la linea)"+" Error sintactico: falta '}' ");}
break;
case 10:
//#line 51 "gramaticaULTIMA.y"
{}
break;
case 11:
//#line 52 "gramaticaULTIMA.y"
{}
break;
case 13:
//#line 58 "gramaticaULTIMA.y"
{ lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"Error sintactico: falta la coma");}
break;
case 14:
//#line 59 "gramaticaULTIMA.y"
{	lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"Error sintactico: falta la coma");}
break;
case 18:
//#line 71 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego el identificador"+ID);}
break;
case 20:
//#line 75 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"falta el ; que separa las variables");}
break;
case 21:
//#line 80 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego un clousure");}
break;
case 23:
//#line 85 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError(" (aca va el numero de la linea)"+"falta el primer parentesis");}
break;
case 24:
//#line 86 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"falta el segundo parentesis");}
break;
case 25:
//#line 87 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"falta la primer llave");}
break;
case 26:
//#line 88 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"falta la segunda llave");}
break;
case 27:
//#line 89 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"faltan ambas llaves");}
break;
case 28:
//#line 90 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"falta ambos parentesis");}
break;
case 37:
//#line 111 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se hizo un print");}
break;
case 39:
//#line 117 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"falta el '(' ");}
break;
case 40:
//#line 118 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"falta el ')'");}
break;
case 41:
//#line 119 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"falta los '(' ')' ");}
break;
case 44:
//#line 127 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("en la linea "+" (aca va el numero de la linea)"+"falta algun parentesis o llave");}
break;
case 45:
//#line 128 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+" falta algun parentesis o llave");}
break;
case 46:
//#line 129 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta algun parentesis o llave ");}
break;
case 47:
//#line 130 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("en la linea "+"(aca va el numero de la linea)"+"falta algun parentesis o llave");}
break;
case 48:
//#line 131 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta algun parentesis o llave ");}
break;
case 49:
//#line 132 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta algun parentesis o llave");}
break;
case 50:
//#line 133 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta algun parentesis o llave ");}
break;
case 52:
//#line 137 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una accion");}
break;
case 55:
//#line 145 "gramaticaULTIMA.y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el ':' ");}
break;
case 56:
//#line 153 "gramaticaULTIMA.y"
{System.out.println("leida DOUBLE");}
break;
case 57:
//#line 154 "gramaticaULTIMA.y"
{System.out.println("Leida CTE");}
break;
case 58:
//#line 158 "gramaticaULTIMA.y"
{System.out.println("cargue un if");}
break;
case 59:
//#line 158 "gramaticaULTIMA.y"
{System.out.println("cargue una condicion");}
break;
case 60:
//#line 158 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una seleccion");}
break;
case 61:
//#line 159 "gramaticaULTIMA.y"
{/*  | error_seleccion
	   ;


error_seleccion : IF  condicion ')' bloque_sentencias ELSE bloque_sentencias END_IF {lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el '(' de la condicion ");}
		| IF '(' condicion   bloque_sentencias ELSE bloque_sentencias END_IF {lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el ')' de la condicion ");}
		| IF  condicion  bloque_sentencias ELSE bloque_sentencias END_IF    {lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"faltan los parentesis de la condicion");}
                ;
*/;}
break;
case 62:
//#line 168 "gramaticaULTIMA.y"
{System.out.println("lei id");}
break;
case 63:
//#line 168 "gramaticaULTIMA.y"
{System.out.println("lei asig");}
break;
case 64:
//#line 168 "gramaticaULTIMA.y"
{System.out.println("lei exp");}
break;
case 65:
//#line 168 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una asignacion");System.out.println("realice una asignacion");}
break;
case 67:
//#line 173 "gramaticaULTIMA.y"
{ System.out.println("Error"); lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el := de la asignacion ");}
break;
case 68:
//#line 174 "gramaticaULTIMA.y"
{ System.out.println("Error"); lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el ID de la asignacion ");}
break;
case 69:
//#line 180 "gramaticaULTIMA.y"
{System.out.println("se hizo una suma ");}
break;
case 70:
//#line 180 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una suma");}
break;
case 71:
//#line 181 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una resta");}
break;
case 72:
//#line 182 "gramaticaULTIMA.y"
{System.out.println("TERMINO a EXPR");}
break;
case 73:
//#line 185 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una multiplicacion");}
break;
case 74:
//#line 186 "gramaticaULTIMA.y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una division");}
break;
case 75:
//#line 187 "gramaticaULTIMA.y"
{ System.out.println("FACTOR a TERMINO"); }
break;
case 76:
//#line 190 "gramaticaULTIMA.y"
{ System.out.println("CTE a FACTOR"); }
break;
case 78:
//#line 192 "gramaticaULTIMA.y"
{System.out.println("cargue un identificador");}
break;
case 79:
//#line 193 "gramaticaULTIMA.y"
{/*| error { System.out.println("Error"); lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el token ");}*/}
break;
case 81:
//#line 200 "gramaticaULTIMA.y"
{System.out.println("llegue aca pero no lee exp");}
break;
case 82:
//#line 200 "gramaticaULTIMA.y"
{System.out.println("encontre un puta expresion");}
break;
case 84:
//#line 203 "gramaticaULTIMA.y"
{System.out.println("cargue un menor");}
break;
//#line 817 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
